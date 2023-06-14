package com.pragma.powerup.restaurantmicroservice.domain.spi;

import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDish;

import java.util.List;

public interface IOrderDishPersistencePort {
    void saveOrderDish(List<OrderDish> orderDish);
}
