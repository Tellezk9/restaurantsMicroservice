package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantPersistencePort {
    void saveRestaurant(Restaurant restaurant);
    List<Restaurant> getRestaurants();
    Restaurant getRestaurantByIdOwnerAndIdRestaurant(Long idOwner, Long idRestaurant);
}
