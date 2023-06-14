package com.pragma.powerup.restaurantmicroservice.domain.spi;

import com.pragma.powerup.restaurantmicroservice.domain.model.Order;

public interface IOrderPersistencePort {
    void saveOrderInformation(Order order);
    Order findOrderInformation(Order order);
}
