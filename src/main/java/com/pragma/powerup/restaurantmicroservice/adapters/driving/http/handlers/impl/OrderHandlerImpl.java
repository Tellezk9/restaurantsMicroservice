package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.OrderDishResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper.IOrderDishResponseMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper.IOrderResponseMapper;
import com.pragma.powerup.restaurantmicroservice.domain.api.IOrderServicePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class OrderHandlerImpl implements IOrderHandler {
    private final IOrderServicePort orderServicePort;
    private final IOrderResponseMapper orderResponseMapper;
    private final IOrderDishResponseMapper orderDishResponseMapper;

    @Override
    public void saveOrder(OrderRequestDto orderRequestDto) {
        orderServicePort.saveOrder(orderRequestDto.getIdRestaurant(), orderRequestDto.getOrderDishes(), orderRequestDto.getAmountDishes());
    }

    @Override
    public List<OrderResponseDto> getOrders(Long status, Long idRestaurant, Integer page) {
        return orderResponseMapper.toListOrderResponseDto(orderServicePort.getOrders(status, idRestaurant, page));
    }

    @Override
    public List<OrderDishResponseDto> getOrderDish(Long idOrder) {
        return orderDishResponseMapper.toListOrderDishResponseDto(orderServicePort.getOrderDishes(idOrder));
    }

    @Override
    public void assignOrder(Long idOrder) {
        orderServicePort.assignOrder(idOrder);
    }

    @Override
    public void changeOrderStatus(Long idOrder, Long status) {
        orderServicePort.changeOrderStatus(idOrder, status);
    }

    @Override
    public void deliverOrder(Long securityCode) {
        orderServicePort.deliverOrder(securityCode);
    }
}
