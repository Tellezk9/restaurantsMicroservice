package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
import com.pragma.powerup.restaurantmicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.auth.IPrincipalUser;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDish;
import com.pragma.powerup.restaurantmicroservice.domain.service.OrderService;
import com.pragma.powerup.restaurantmicroservice.domain.service.Validator;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IEmployeePersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IOrderDishPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IOrderPersistencePort;

import java.util.List;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IOrderDishPersistencePort orderDishPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    private final IEmployeePersistencePort employeePersistencePort;
    private final IPrincipalUser authUser;
    private final Validator validator;
    private final OrderService orderService;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IDishPersistencePort dishPersistencePort, IOrderDishPersistencePort orderDishPersistencePort, IEmployeePersistencePort employeePersistencePort, IPrincipalUser authUser) {
        this.orderPersistencePort = orderPersistencePort;
        this.orderDishPersistencePort = orderDishPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
        this.employeePersistencePort = employeePersistencePort;
        this.authUser = authUser;
        this.validator = new Validator();
        this.orderService = new OrderService();

    }

    @Override
    public void saveOrder(Long idRestaurant, List<Long> orderDishes, List<Integer> amountDishes) {
        validator.isIdValid(Integer.valueOf(String.valueOf(idRestaurant)));
        orderService.isOrderDishesAndAmountDishesValid(orderDishes, amountDishes);

        List<Dish> dishList = dishPersistencePort.getDishesByRestaurantId(idRestaurant);

        orderService.restaurantHasTheDishes(dishList, orderDishes);

        Order orderInformation = orderService.makeNewOrderInformation(authUser.getIdUser(), idRestaurant, Constants.ORDER_STATUS_PENDING);
        orderPersistencePort.saveOrderInformation(orderInformation);

        orderInformation = orderPersistencePort.findOrderInformation(orderService.makeNewOrderInformation(authUser.getIdUser(), idRestaurant, Constants.ORDER_STATUS_PENDING));

        List<OrderDish> orderDishList = orderService.makeNewListOrderDish(orderInformation.getId(), orderDishes, amountDishes);

        orderDishPersistencePort.saveOrderDish(orderDishList);
    }

    @Override
    public List<Order> getOrders(Long status, Long idRestaurant, Integer page) {
        validator.hasRoleValid(authUser.getRole(), Constants.EMPLOYEE_ROLE_NAME);
        validator.isIdValid(Integer.valueOf(String.valueOf(status)));
        validator.isIdValid(Integer.valueOf(String.valueOf(idRestaurant)));

        employeePersistencePort.getEmployeeByIdEmployeeAndIdRestaurant(authUser.getIdUser(), idRestaurant);

        return orderPersistencePort.getOrdersPageable(status, idRestaurant, page);
    }

    @Override
    public List<OrderDish> getOrderDishes(Long idOrder) {
        validator.hasRoleValid(authUser.getRole(), Constants.EMPLOYEE_ROLE_NAME);
        validator.isIdValid(Integer.valueOf(String.valueOf(idOrder)));

        return orderDishPersistencePort.getOrderDishes(idOrder);
    }

    @Override
    public void assignOrder(Long idOrder) {
        validator.hasRoleValid(authUser.getRole(), Constants.EMPLOYEE_ROLE_NAME);
        validator.isIdValid(Integer.valueOf(String.valueOf(idOrder)));

        Order order = orderPersistencePort.getOrder(idOrder);
        employeePersistencePort.getEmployeeByIdEmployeeAndIdRestaurant(authUser.getIdUser(),order.getRestaurant().getId());

        orderPersistencePort.assignOrder(idOrder, authUser.getIdUser(), Constants.ORDER_STATUS_PREPARING);
    }

    @Override
    public void changeOrderStatus(Long idOrder, Long status) {
        validator.hasRoleValid(authUser.getRole(), Constants.EMPLOYEE_ROLE_NAME);
        validator.isIdValid(Integer.valueOf(String.valueOf(idOrder)));
        validator.isIdValid(Integer.valueOf(String.valueOf(status)));

        orderService.isValidStatus(status);

        Order order = orderPersistencePort.getOrder(idOrder);
        employeePersistencePort.getEmployeeByIdEmployeeAndIdRestaurant(authUser.getIdUser(),order.getRestaurant().getId());

        orderPersistencePort.changeOrderStatus(idOrder,status);
    }
}
