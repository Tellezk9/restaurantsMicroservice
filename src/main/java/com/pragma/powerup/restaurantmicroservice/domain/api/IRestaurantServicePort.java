package com.pragma.powerup.restaurantmicroservice.domain.api;

import com.pragma.powerup.restaurantmicroservice.domain.model.Employee;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantServicePort {
    void saveRestaurant(Restaurant restaurant);
    Restaurant getRestaurant(Integer idOwner, Integer idRestaurant);
    List<Restaurant> getRestaurants(Integer page);
    List<Restaurant> getOwnerRestaurants(Integer idOwner);
    void saveRestaurantEmployee(Employee employee);
}
