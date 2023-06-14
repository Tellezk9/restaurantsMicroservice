package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.restaurantmicroservice.domain.api.IOrderServicePort;
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
}