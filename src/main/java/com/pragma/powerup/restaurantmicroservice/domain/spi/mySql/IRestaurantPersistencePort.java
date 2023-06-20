package com.pragma.powerup.restaurantmicroservice.domain.spi.mySql;

import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantPersistencePort {
    void saveRestaurant(Restaurant restaurant);
    Restaurant getRestaurant(Long idRestaurant);
    List<String[]> getRestaurants(Integer page);
    List<Restaurant> getOwnerRestaurants(Long idOwner);
    Restaurant getRestaurantByIdOwnerAndIdRestaurant(Long idOwner, Long idRestaurant);
}
