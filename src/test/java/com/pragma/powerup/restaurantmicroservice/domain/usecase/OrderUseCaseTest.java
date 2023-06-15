package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
import com.pragma.powerup.restaurantmicroservice.domain.auth.IPrincipalUser;
import com.pragma.powerup.restaurantmicroservice.domain.model.*;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IEmployeePersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IOrderDishPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IOrderPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {

    @Mock
    private IOrderPersistencePort orderPersistencePort;
    @Mock
    private IOrderDishPersistencePort orderDishPersistencePort;
    @Mock
    private IDishPersistencePort dishPersistencePort;
    @Mock
    private IEmployeePersistencePort employeePersistencePort;
    @Mock
    private IPrincipalUser authUser;
    @InjectMocks
    private OrderUseCase orderUseCase;

    @Test
    void saveOrder() {
        Long idUser = 1L;
        Long idRestaurant = 2L;
        List<Long> orderDishes = List.of(1L);
        List<Integer> amountDishes = List.of(2);

        List<Dish> dishList = List.of(new Dish(1L, null, null, null, null, null, null, null));
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(1L, 1L, null, 2L, null, restaurant);

        when(dishPersistencePort.getDishesByRestaurantId(idRestaurant)).thenReturn(dishList);

        when(authUser.getIdUser()).thenReturn(idUser);
        when(orderPersistencePort.findOrderInformation(Mockito.any(Order.class))).thenReturn(order);
        doNothing().when(orderDishPersistencePort).saveOrderDish(Mockito.any());
        doNothing().when(orderPersistencePort).saveOrderInformation(Mockito.any(Order.class));

        orderUseCase.saveOrder(idRestaurant, orderDishes, amountDishes);

        verify(dishPersistencePort, times(1)).getDishesByRestaurantId(idRestaurant);
        verify(orderPersistencePort, times(1)).saveOrderInformation(Mockito.any(Order.class));
        verify(orderPersistencePort, times(1)).findOrderInformation(Mockito.any(Order.class));
        verify(orderDishPersistencePort, times(1)).saveOrderDish(Mockito.any());
    }

    @Test
    void getOrders() {
        Long status = 1L;
        Long idRestaurant = 1L;
        Long idUser = 1L;
        Integer page = 1;
        String role = "ROLE_EMPLOYEE";
        Employee employee = new Employee(null, idUser, null);
        Restaurant restaurant = new Restaurant(idRestaurant, null, null, null, null, null, null);
        List<Order> orderList = List.of(new Order(null, idUser, null, status, null, restaurant));

        when(authUser.getIdUser()).thenReturn(idUser);
        when(authUser.getRole()).thenReturn(role);
        when(employeePersistencePort.getEmployeeByIdEmployeeAndIdRestaurant(idUser, idRestaurant)).thenReturn(employee);
        when(orderPersistencePort.getOrdersPageable(status, idRestaurant, page)).thenReturn(orderList);

        orderUseCase.getOrders(status, idRestaurant, page);

        verify(employeePersistencePort, times(1)).getEmployeeByIdEmployeeAndIdRestaurant(idUser, idRestaurant);
        verify(orderPersistencePort, times(1)).getOrdersPageable(status, idRestaurant, page);
    }

    @Test
    void getOrderDishes() {
        Long idOrder = 1L;
        String nameDish = "testDish";
        String role = "ROLE_EMPLOYEE";
        Integer amount = 1;

        Order order = new Order(idOrder, null, null, null, null, null);
        Dish dish = new Dish(1L, nameDish, null, null, null, null, null, null);
        List<OrderDish> orderDishList = List.of(new OrderDish(order, dish, amount));

        when(authUser.getRole()).thenReturn(role);
        when(orderDishPersistencePort.getOrderDishes(idOrder)).thenReturn(orderDishList);

        orderUseCase.getOrderDishes(idOrder);

        verify(orderDishPersistencePort, times(1)).getOrderDishes(idOrder);
    }

    @Test
    void assignOrder() {
        Long idOrder = 1L;
        Long idUser = 1L;
        String role = "ROLE_EMPLOYEE";

        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(idOrder, null, null, null, idUser, restaurant);
        Employee employee = new Employee(1L, idUser, restaurant);

        when(authUser.getIdUser()).thenReturn(idUser);
        when(authUser.getRole()).thenReturn(role);
        when(orderPersistencePort.getOrder(idOrder)).thenReturn(order);
        when(employeePersistencePort.getEmployeeByIdEmployeeAndIdRestaurant(idUser, order.getRestaurant().getId())).thenReturn(employee);
        doNothing().when(orderPersistencePort).assignOrder(idOrder, idUser, Constants.ORDER_STATUS_PREPARING);

        orderUseCase.assignOrder(idOrder);

        verify(orderPersistencePort, times(1)).getOrder(idOrder);
        verify(orderPersistencePort, times(1)).assignOrder(idOrder, idUser, Constants.ORDER_STATUS_PREPARING);
        verify(employeePersistencePort, times(1)).getEmployeeByIdEmployeeAndIdRestaurant(idUser, order.getRestaurant().getId());
    }
    @Test
    void changeOrderStatus() {
        Long idOrder = 1L;
        Long status = 1L;
        Long idUser = 1L;
        String role = "ROLE_EMPLOYEE";

        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(idOrder, null, null, null, idUser, restaurant);
        Employee employee = new Employee(1L, idUser, restaurant);

        when(authUser.getIdUser()).thenReturn(idUser);
        when(authUser.getRole()).thenReturn(role);
        when(orderPersistencePort.getOrder(idOrder)).thenReturn(order);
        when(employeePersistencePort.getEmployeeByIdEmployeeAndIdRestaurant(idUser, order.getRestaurant().getId())).thenReturn(employee);
        doNothing().when(orderPersistencePort).changeOrderStatus(idOrder,status);

        orderUseCase.changeOrderStatus(idOrder,status);

        verify(orderPersistencePort, times(1)).getOrder(idOrder);
        verify(orderPersistencePort, times(1)).changeOrderStatus(idOrder,status);
        verify(employeePersistencePort, times(1)).getEmployeeByIdEmployeeAndIdRestaurant(idUser, order.getRestaurant().getId());
    }
}