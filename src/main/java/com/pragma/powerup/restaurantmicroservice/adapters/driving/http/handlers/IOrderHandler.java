package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.OrderDishResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.OrderResponseDto;

import java.util.List;

public interface IOrderHandler {
    void saveOrder(OrderRequestDto orderRequestDto);
    List<OrderResponseDto> getOrders(Long status, Long idRestaurant, Integer page);
    List<OrderDishResponseDto> getOrderDish(Long idOrder);
    void assignOrder(Long idOrder);
    void changeOrderStatus(Long idOrder,Long status);
    void deliverOrder(Long securityCode);
    void cancelOrder(Long idOrder);
}
