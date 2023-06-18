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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
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
        Order order = new Order(null, 1L, null, 2L, null, null,restaurant);

        RestaurantEntity restaurantEntity = new RestaurantEntity(1L, null, null, null, null, null, null);
        OrderEntity orderEntity = new OrderEntity(1L, 1L, null, 2L, null, restaurantEntity,null);

        when(orderRepository.findByIdClientAndRestaurantEntityIdAndStatusNot(order.getIdClient(), order.getRestaurant().getId(), Constants.ORDER_STATUS_OK)).thenReturn(Optional.empty());
        when(orderEntityMapper.toOrderEntity(order)).thenReturn(orderEntity);

        orderMysqlAdapter.saveOrderInformation(order);

        verify(orderEntityMapper, times(1)).toOrderEntity(order);
    }

    @Test
    void saveOrderInformationConflict() {
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(null, 1L, null, 2L, null, null,restaurant);

        RestaurantEntity restaurantEntity = new RestaurantEntity(1L, null, null, null, null, null, null);
        OrderEntity orderEntity = new OrderEntity(1L, 1L, null, 2L, null, restaurantEntity,null);

        when(orderRepository.findByIdClientAndRestaurantEntityIdAndStatusNot(order.getIdClient(), order.getRestaurant().getId(), Constants.ORDER_STATUS_OK)).thenReturn(Optional.of(orderEntity));

        assertThrows(ClientHasPendingOrderException.class, () -> orderMysqlAdapter.saveOrderInformation(order));
    }

    @Test
    void findOrderInformation() {
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(null, 1L, null, 2L, null, null,restaurant);

        RestaurantEntity restaurantEntity = new RestaurantEntity(1L, null, null, null, null, null, null);
        OrderEntity orderEntity = new OrderEntity(1L, 1L, null, 2L, null, restaurantEntity,null);

        when(orderRepository.findByIdClientAndRestaurantEntityIdAndStatus(order.getIdClient(), order.getRestaurant().getId(), order.getStatus())).thenReturn(Optional.of(orderEntity));
        when(orderEntityMapper.toOrder(orderEntity)).thenReturn(order);

        orderMysqlAdapter.findOrderInformation(order);

        verify(orderEntityMapper, times(1)).toOrder(orderEntity);
    }

    @Test
    void findOrderInformationConflict() {
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(null, 1L, null, 2L, null, null,restaurant);

        when(orderRepository.findByIdClientAndRestaurantEntityIdAndStatus(order.getIdClient(), order.getRestaurant().getId(), order.getStatus())).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderMysqlAdapter.findOrderInformation(order));
    }

    @Test
    void getOrdersPageable() {
        Long idRestaurant = 1L;
        Long status = 1L;
        int page = 1;
        Pageable pageable = PageRequest.of(page, Constants.MAX_PAGE_SIZE);
        List<OrderEntity> orderEntityList = List.of(new OrderEntity());
        List<Order> orderList = List.of(new Order(null, null, null, null, null, null,null));

        when(orderRepository.findByRestaurantEntityIdAndStatus(idRestaurant, status, pageable)).thenReturn(orderEntityList);
        when(orderEntityMapper.toOrderList(orderEntityList)).thenReturn(orderList);

        orderMysqlAdapter.getOrdersPageable(status, idRestaurant, page);

        verify(orderRepository, times(1)).findByRestaurantEntityIdAndStatus(idRestaurant, status, pageable);
        verify(orderEntityMapper, times(1)).toOrderList(orderEntityList);
    }

    @Test
    void getOrdersPageable_Conflict() {
        Long idRestaurant = 1L;
        Long status = 1L;
        int page = 1;
        Pageable pageable = PageRequest.of(page, Constants.MAX_PAGE_SIZE);
        List<OrderEntity> orderEntityList = List.of();

        when(orderRepository.findByRestaurantEntityIdAndStatus(idRestaurant, status, pageable)).thenReturn(orderEntityList);

        assertThrows(OrderNotFoundException.class, () -> orderMysqlAdapter.getOrdersPageable(status, idRestaurant, page));
    }

    @Test
    void assignOrder() {
        Long idOrder = 1L;
        Long idEmployee = 1L;
        Long status = 1L;

        OrderEntity orderEntity = new OrderEntity(idOrder, null, null, status, idEmployee,null,null);

        when(orderRepository.findById(idOrder)).thenReturn(Optional.of(orderEntity));

        orderMysqlAdapter.assignOrder(idOrder, idEmployee, status);

        verify(orderRepository, times(1)).findById(idOrder);
    }

    @Test
    void assignOrder_Conflict() {
        Long idOrder = 1L;
        Long idEmployee = 1L;
        Long status = 1L;

        when(orderRepository.findById(idOrder)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderMysqlAdapter.assignOrder(idOrder, idEmployee, status));
        verify(orderRepository, times(1)).findById(idOrder);
    }

    @Test
    void changeOrderStatus() {
        Long idOrder = 1L;
        Long status = 1L;

        OrderEntity orderEntity = new OrderEntity(idOrder, null, null, status, null, null,null);

        when(orderRepository.findById(idOrder)).thenReturn(Optional.of(orderEntity));

        orderMysqlAdapter.changeOrderStatus(idOrder, status);

        verify(orderRepository, times(1)).findById(idOrder);
    }

    @Test
    void changeOrderStatus_Conflict() {
        Long idOrder = 1L;
        Long status = 1L;

        when(orderRepository.findById(idOrder)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderMysqlAdapter.changeOrderStatus(idOrder, status));
        verify(orderRepository, times(1)).findById(idOrder);
    }

}