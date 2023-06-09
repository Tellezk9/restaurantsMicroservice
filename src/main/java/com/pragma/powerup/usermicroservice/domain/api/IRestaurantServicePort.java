package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.Employee;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantServicePort {
    void saveRestaurant(Restaurant restaurant);
    Restaurant getRestaurant(Integer idOwner, Integer idRestaurant);
    List<Restaurant> getRestaurants();
    List<Restaurant> getOwnerRestaurants(Integer idOwner);
    void saveRestaurantEmployee(Employee employee);
}
