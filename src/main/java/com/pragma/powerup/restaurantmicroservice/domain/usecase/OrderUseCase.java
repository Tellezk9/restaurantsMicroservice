package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
import com.pragma.powerup.restaurantmicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.auth.IPrincipalUser;
import com.pragma.powerup.restaurantmicroservice.domain.geteway.IHttpAdapter;
import com.pragma.powerup.restaurantmicroservice.domain.model.*;
import com.pragma.powerup.restaurantmicroservice.domain.service.OrderService;
import com.pragma.powerup.restaurantmicroservice.domain.service.Validator;
import com.pragma.powerup.restaurantmicroservice.domain.spi.mongo.IOrderCollectionPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.mySql.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IOrderCollectionPersistencePort orderCollectionPersistencePort;
    private final IOrderDishPersistencePort orderDishPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    private final IEmployeePersistencePort employeePersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IPrincipalUser authUser;
    private final IHttpAdapter httpAdapter;
    private final Validator validator;
    private final OrderService orderService;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IOrderCollectionPersistencePort orderCollectionPersistencePort, IDishPersistencePort dishPersistencePort, IOrderDishPersistencePort orderDishPersistencePort, IEmployeePersistencePort employeePersistencePort, IRestaurantPersistencePort restaurantPersistencePort, IPrincipalUser authUser, IHttpAdapter httpAdapter) {
        this.orderPersistencePort = orderPersistencePort;
        this.orderCollectionPersistencePort = orderCollectionPersistencePort;
        this.orderDishPersistencePort = orderDishPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
        this.employeePersistencePort = employeePersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.authUser = authUser;
        this.httpAdapter = httpAdapter;
        this.validator = new Validator();
        this.orderService = new OrderService();
    }

    public void saveOrder(Long idRestaurant, List<Long> orderDishes, List<Integer> amountDishes) {
        validator.isIdValid(Integer.valueOf(String.valueOf(idRestaurant)));
        orderService.isOrderDishesAndAmountDishesValid(orderDishes, amountDishes);

        List<Dish> dishList = dishPersistencePort.getDishesByRestaurantId(idRestaurant);

        orderService.restaurantHasTheDishes(dishList, orderDishes);

        //Guarda orden en la base de datos
        Order orderInformation = orderService.makeNewOrderInformation(authUser.getIdUser(), idRestaurant, Constants.ORDER_STATUS_PENDING);
        orderPersistencePort.saveOrderInformation(orderInformation);

        orderInformation = orderPersistencePort.findOrderInformation(orderService.makeNewOrderInformation(authUser.getIdUser(), idRestaurant, Constants.ORDER_STATUS_PENDING));

        List<OrderDish> orderDishList = orderService.makeNewListOrderDish(orderInformation.getId(), orderDishes, amountDishes);

        //Guarda los platos de la orden en la base de datos
        orderDishPersistencePort.saveOrderDish(orderDishList);

        //Guarda la trazabilidad en la base de datos de mongo
        List<Map<String, String>> dishesMapped = orderService.getNameDishes(dishList, orderDishes, amountDishes);
        saveTraceabilityOrder(orderInformation, dishesMapped);
    }

    public List<Order> getOrders(Long status, Long idRestaurant, Integer page) {
        validator.hasRoleValid(authUser.getRole(), Constants.EMPLOYEE_ROLE_NAME);
        validator.isIdValid(Integer.valueOf(String.valueOf(status)));
        validator.isIdValid(Integer.valueOf(String.valueOf(idRestaurant)));

        employeePersistencePort.getEmployeeByIdEmployeeAndIdRestaurant(authUser.getIdUser(), idRestaurant);

        return orderPersistencePort.getOrdersPageable(status, idRestaurant, page);
    }

    public List<OrderDish> getOrderDishes(Long idOrder) {
        validator.hasRoleValid(authUser.getRole(), Constants.EMPLOYEE_ROLE_NAME);
        validator.isIdValid(Integer.valueOf(String.valueOf(idOrder)));

        return orderDishPersistencePort.getOrderDishes(idOrder);
    }

    public void assignOrder(Long idOrder) {
        validator.hasRoleValid(authUser.getRole(), Constants.EMPLOYEE_ROLE_NAME);
        validator.isIdValid(Integer.valueOf(String.valueOf(idOrder)));

        Order order = orderPersistencePort.getOrder(idOrder);
        employeePersistencePort.getEmployeeByIdEmployeeAndIdRestaurant(authUser.getIdUser(), order.getRestaurant().getId());

        orderPersistencePort.assignOrder(idOrder, authUser.getIdUser(), Constants.ORDER_STATUS_PREPARING);

        assignOrderCollection(idOrder, authUser.getIdUser(), Constants.ORDER_STATUS_PREPARING);
    }

    public void changeOrderStatus(Long idOrder, Long status) {
        validator.hasRoleValid(authUser.getRole(), Constants.EMPLOYEE_ROLE_NAME);
        validator.isIdValid(Integer.valueOf(String.valueOf(idOrder)));
        validator.isIdValid(Integer.valueOf(String.valueOf(status)));

        orderService.isValidStatus(status);

        Order order = orderPersistencePort.getOrder(idOrder);
        employeePersistencePort.getEmployeeByIdEmployeeAndIdRestaurant(authUser.getIdUser(), order.getRestaurant().getId());

        if (status.equals(Constants.ORDER_STATUS_OK)) {
            sendNotification(order);
        }
        orderPersistencePort.changeOrderStatus(idOrder, status);
        changeOrderCollectionStatus(idOrder, status);
    }

    public void deliverOrder(Long securityCode) {
        validator.hasRoleValid(authUser.getRole(), Constants.EMPLOYEE_ROLE_NAME);
        validator.isIdValid(Integer.valueOf(String.valueOf(securityCode)));

        Employee employee = employeePersistencePort.getEmployeeById(authUser.getIdUser());

        orderPersistencePort.deliverOrder(securityCode, employee.getIdRestaurant().getId(), Constants.ORDER_STATUS_DELIVERED);

        Order order = orderPersistencePort.getOrderBySecurityPinAndIdRestaurant(securityCode, employee.getIdRestaurant().getId());
        updateOrderCollectionStatus(order.getId(), Constants.ORDER_STATUS_DELIVERED);
    }

    public void cancelOrder(Long idOrder) {
        validator.hasRoleValid(authUser.getRole(), Constants.CLIENT_ROLE_NAME);
        validator.isIdValid(Integer.valueOf(String.valueOf(idOrder)));

        orderPersistencePort.cancelOrder(idOrder, authUser.getIdUser(), Constants.ORDER_STATUS_CANCELED);
        updateOrderCollectionStatus(idOrder, Constants.ORDER_STATUS_CANCELED);
    }

    public void sendNotification(Order order) {
        validator.hasRoleValid(authUser.getRole(), Constants.EMPLOYEE_ROLE_NAME);

        Client client = httpAdapter.getClient(order.getIdClient(), authUser.getToken());
        client.setSecurityPin(order.getSecurityPin());

        httpAdapter.sendNotification(client, authUser.getToken());
    }

    public void saveTraceabilityOrder(Order orderInformation, List<Map<String, String>> dishesMapped) {
        OrderDocument orderDocument = orderService.makeNewOrderDocument(orderInformation, dishesMapped);
        orderCollectionPersistencePort.saveOrderCollection(orderDocument);
    }

    public OrderDocument getTraceabilityOrder(Long idOrder) {
        validator.hasRoleValid(authUser.getRole(), Constants.CLIENT_ROLE_NAME);

        return orderCollectionPersistencePort.getOrderCollectionByIdOrder(idOrder);
    }

    public List<OrderDocument> getTraceabilityOrders() {
        validator.hasRoleValid(authUser.getRole(), Constants.CLIENT_ROLE_NAME);

        return orderCollectionPersistencePort.getOrderCollectionsByIdClient(authUser.getIdUser());
    }

    public List<OrderDocument> getOrdersDuration(Long idRestaurant) {
        validator.hasRoleValid(authUser.getRole(), Constants.OWNER_ROLE_NAME);
        validator.isIdValid(Integer.valueOf(String.valueOf(idRestaurant)));

        restaurantPersistencePort.getRestaurantByIdOwnerAndIdRestaurant(authUser.getIdUser(), idRestaurant);

        return orderCollectionPersistencePort.getOrdersDuration(idRestaurant);
    }

    public List<RankingEmployee> getRankingEmployeesByRestaurant(Long idRestaurant){
        validator.hasRoleValid(authUser.getRole(), Constants.OWNER_ROLE_NAME);

        restaurantPersistencePort.getRestaurantByIdOwnerAndIdRestaurant(authUser.getIdUser(), idRestaurant);

        return orderService.calculateAverageTimesBetweenOrders(orderCollectionPersistencePort.getOrdersByIdRestaurantOrderedByIdEmployee(idRestaurant));
    }

    public void assignOrderCollection(Long idOrder, Long idEmployee, Long newStatus) {
        validator.hasRoleValid(authUser.getRole(), Constants.EMPLOYEE_ROLE_NAME);

        orderCollectionPersistencePort.assignOrderCollection(idOrder, idEmployee, newStatus);
    }

    public void changeOrderCollectionStatus(Long idOrder, Long newStatus) {
        Date finalDate = null;
        if (newStatus.equals(Constants.ORDER_STATUS_OK)) {
            finalDate = new Date();
        }
        orderCollectionPersistencePort.changeOrderCollectionStatus(idOrder, newStatus, finalDate);
    }

    public void updateOrderCollectionStatus(Long idOrder, Long newStatus) {
        orderCollectionPersistencePort.updateOrderCollectionStatus(idOrder, newStatus);
    }
}
