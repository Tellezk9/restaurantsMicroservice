package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.domain.auth.IPrincipalUser;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IDishPersistencePort;
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
}