package com.pragma.powerup.restaurantmicroservice.domain.spi;

import com.pragma.powerup.restaurantmicroservice.domain.model.Order;

import java.util.List;

public interface IOrderPersistencePort {
    void saveOrderInformation(Order order);
    Order findOrderInformation(Order order);
    List<Order> getOrdersPageable(Long status, Long idRestaurant,Integer page);
    Order getOrder(Long idOrder);
    void assignOrder(Long idOrder,Long idEmployee, Long status);
    void changeOrderStatus(Long idOrder, Long status);
}
