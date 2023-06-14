package com.pragma.powerup.restaurantmicroservice.domain.api;

import java.util.List;

public interface IOrderServicePort {
    void saveOrder(Long idRestaurant, List<Long>orderDishes, List<Integer>amountDishes);
}
