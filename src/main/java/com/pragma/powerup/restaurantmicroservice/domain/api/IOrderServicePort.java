package com.pragma.powerup.restaurantmicroservice.domain.api;

import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDish;

import java.util.List;

public interface IOrderServicePort {
    void saveOrder(Long idRestaurant, List<Long> orderDishes, List<Integer> amountDishes);
    List<Order> getOrders(Long status, Long idRestaurant, Integer page);
    List<OrderDish> getOrderDishes(Long idOrder);
}
