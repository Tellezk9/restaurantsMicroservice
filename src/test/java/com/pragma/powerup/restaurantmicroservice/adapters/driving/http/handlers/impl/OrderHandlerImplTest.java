package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.OrderDishResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper.IOrderDishResponseMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper.IOrderResponseMapper;
import com.pragma.powerup.restaurantmicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderHandlerImplTest {
    @Mock
    private IOrderServicePort orderServicePort;
    @Mock
    private IOrderResponseMapper orderResponseMapper;
    @Mock
    private IOrderDishResponseMapper orderDishResponseMapper;
    @InjectMocks
    private OrderHandlerImpl orderHandler;

    @Test
    void saveOrder() {
        List<Long> orderDishes = List.of(1L);
        List<Integer> amountDishes = List.of(1);
        OrderRequestDto orderRequestDto = new OrderRequestDto(1L, orderDishes, amountDishes);

        orderHandler.saveOrder(orderRequestDto);

        verify(orderServicePort, times(1)).saveOrder(orderRequestDto.getIdRestaurant(), orderRequestDto.getOrderDishes(), orderRequestDto.getAmountDishes());
    }

    @Test
    void getOrders() {
        Long status = 1L;
        Long idRestaurant = 1L;
        Integer page = 1;
        Restaurant restaurant = new Restaurant(idRestaurant, null, null, null, null, null, null);
        List<Order> orderList = List.of(new Order(1L, null, null, status, null, null, restaurant));
        List<OrderResponseDto> orderResponseDtoList = List.of(new OrderResponseDto(null, null, null, status, null, idRestaurant));

        when(orderServicePort.getOrders(status, idRestaurant, page)).thenReturn(orderList);
        when(orderResponseMapper.toListOrderResponseDto(orderList)).thenReturn(orderResponseDtoList);

        orderHandler.getOrders(status, idRestaurant, page);

        verify(orderServicePort, times(1)).getOrders(status, idRestaurant, page);
        verify(orderResponseMapper, times(1)).toListOrderResponseDto(orderList);
    }

    @Test
    void getOrderDish() {
        Long idOrder = 1L;
        String nameDish = "testDish";
        Integer amount = 1;

        Order order = new Order(idOrder, null, null, null, null, null, null);
        Dish dish = new Dish(null, nameDish, null, null, null, null, null, null);
        List<OrderDish> orderDishList = List.of(new OrderDish(order, dish, amount));

        List<OrderDishResponseDto> orderDishResponseDto = List.of(new OrderDishResponseDto(nameDish, amount));

        when(orderServicePort.getOrderDishes(idOrder)).thenReturn(orderDishList);
        when(orderDishResponseMapper.toListOrderDishResponseDto(orderDishList)).thenReturn(orderDishResponseDto);

        orderHandler.getOrderDish(idOrder);

        verify(orderServicePort, times(1)).getOrderDishes(idOrder);
        verify(orderDishResponseMapper, times(1)).toListOrderDishResponseDto(orderDishList);
    }

    @Test
    void assignOrder() {
        Long idOrder = 1L;
        doNothing().when(orderServicePort).assignOrder(idOrder);
        orderHandler.assignOrder(idOrder);

        verify(orderServicePort, times(1)).assignOrder(idOrder);
    }

    @Test
    void changeOrderStatus() {
        Long idOrder = 1L;
        Long status = 1L;

        doNothing().when(orderServicePort).changeOrderStatus(idOrder, status);
        orderHandler.changeOrderStatus(idOrder, status);

        verify(orderServicePort, times(1)).changeOrderStatus(idOrder, status);
    }

    @Test
    void deliverOrder() {
        Long securityCode = 1L;

        doNothing().when(orderServicePort).deliverOrder(securityCode);

        orderHandler.deliverOrder(securityCode);

        verify(orderServicePort, times(1)).deliverOrder(securityCode);
    }

    @Test
    void cancelOrder() {
        Long idOrder = 1L;

        doNothing().when(orderServicePort).cancelOrder(idOrder);

        orderHandler.cancelOrder(idOrder);

        verify(orderServicePort, times(1)).cancelOrder(idOrder);
    }

    @Test
    void getOrdersDuration() {
        Long idRestaurant = 1L;
        OrderDocument orderDocument = new OrderDocument(null,null,null,null,null,null,null,null,null,null);

        when(orderServicePort.getOrdersDuration(idRestaurant)).thenReturn(List.of(orderDocument));

        orderHandler.getOrdersDuration(idRestaurant);

        verify(orderServicePort, times(1)).getOrdersDuration(idRestaurant);
    }

    @Test
    void getRankingEmployeesByRestaurant() {
        Long idRestaurant = 1L;
        RankingEmployee rankingEmployee = new RankingEmployee(1L,null);

        when(orderServicePort.getRankingEmployeesByRestaurant(idRestaurant)).thenReturn(List.of(rankingEmployee));

        orderHandler.getRankingEmployees(idRestaurant);

        verify(orderServicePort, times(1)).getRankingEmployeesByRestaurant(idRestaurant);
    }
}