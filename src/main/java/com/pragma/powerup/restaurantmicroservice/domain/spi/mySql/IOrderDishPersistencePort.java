package com.pragma.powerup.restaurantmicroservice.domain.spi.mySql;

import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDish;

import java.util.List;

public interface IOrderDishPersistencePort {
    void saveOrderDish(List<OrderDish> orderDish);
    List<OrderDish> getOrderDishPageable(Long status, Long idRestaurant,Integer page);
    List<OrderDish> getOrderDishes(Long idOrder);
}
