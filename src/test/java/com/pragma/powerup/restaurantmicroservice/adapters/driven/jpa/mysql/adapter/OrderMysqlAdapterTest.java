package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.ClientHasPendingOrderException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.OrderNotFoundException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers.IOrderEntityMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderMysqlAdapterTest {
    @Mock
    private IOrderRepository orderRepository;
    @Mock
    private IOrderEntityMapper orderEntityMapper;
    @InjectMocks
    private OrderMysqlAdapter orderMysqlAdapter;

    @Test
    void saveOrderInformation() {
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(null, 1L, null, 2L, null, restaurant);

        RestaurantEntity restaurantEntity = new RestaurantEntity(1L, null, null, null, null, null, null);
        OrderEntity orderEntity = new OrderEntity(1L, 1L, null, 2L, null, restaurantEntity);

        when(orderRepository.findByIdClientAndRestaurantEntityIdAndStatusNot(order.getIdClient(), order.getIdRestaurant().getId(), Constants.ORDER_STATUS_OK)).thenReturn(Optional.empty());
        when(orderEntityMapper.toOrderEntity(order)).thenReturn(orderEntity);

        orderMysqlAdapter.saveOrderInformation(order);

        verify(orderEntityMapper,times(1)).toOrderEntity(order);
    }
    @Test
    void saveOrderInformationConflict() {
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(null, 1L, null, 2L, null, restaurant);

        RestaurantEntity restaurantEntity = new RestaurantEntity(1L, null, null, null, null, null, null);
        OrderEntity orderEntity = new OrderEntity(1L, 1L, null, 2L, null, restaurantEntity);

        when(orderRepository.findByIdClientAndRestaurantEntityIdAndStatusNot(order.getIdClient(), order.getIdRestaurant().getId(), Constants.ORDER_STATUS_OK)).thenReturn(Optional.of(orderEntity));

        assertThrows(ClientHasPendingOrderException.class,()->orderMysqlAdapter.saveOrderInformation(order));
    }

    @Test
    void findOrderInformation() {
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(null, 1L, null, 2L, null, restaurant);

        RestaurantEntity restaurantEntity = new RestaurantEntity(1L, null, null, null, null, null, null);
        OrderEntity orderEntity = new OrderEntity(1L, 1L, null, 2L, null, restaurantEntity);

        when(orderRepository.findByIdClientAndRestaurantEntityIdAndStatus(order.getIdClient(), order.getIdRestaurant().getId(), order.getStatus())).thenReturn(Optional.of(orderEntity));
        when(orderEntityMapper.toOrder(orderEntity)).thenReturn(order);

        orderMysqlAdapter.findOrderInformation(order);

        verify(orderEntityMapper,times(1)).toOrder(orderEntity);
    }
    @Test
    void findOrderInformationConflict() {
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(null, 1L, null, 2L, null, restaurant);

        when(orderRepository.findByIdClientAndRestaurantEntityIdAndStatus(order.getIdClient(), order.getIdRestaurant().getId(), order.getStatus())).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,()->orderMysqlAdapter.findOrderInformation(order));

    }
}