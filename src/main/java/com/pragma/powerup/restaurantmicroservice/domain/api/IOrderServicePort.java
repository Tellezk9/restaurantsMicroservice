package com.pragma.powerup.restaurantmicroservice.domain.api;

import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDish;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDocument;

import java.util.List;

public interface IOrderServicePort {
    void saveOrder(Long idRestaurant, List<Long> orderDishes, List<Integer> amountDishes);
    List<Order> getOrders(Long status, Long idRestaurant, Integer page);
    List<OrderDish> getOrderDishes(Long idOrder);
    void assignOrder(Long idOrder);
    void changeOrderStatus(Long idOrder, Long status);
    void deliverOrder(Long securityCode);
    void cancelOrder(Long idOrder);
    OrderDocument getTraceabilityOrder(Long idOrder);
    List<OrderDocument> getTraceabilityOrders();
}
