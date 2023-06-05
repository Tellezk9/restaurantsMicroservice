package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantPersistencePort {
    void saveRestaurant(Restaurant restaurant);
    Restaurant getRestaurant(Long idRestaurant);
    List<Restaurant> getRestaurants();
    List<Restaurant> getOwnerRestaurants(Long idOwner);
    Restaurant getRestaurantByIdOwnerAndIdRestaurant(Long idOwner, Long idRestaurant);
}
