package com.pragma.powerup.restaurantmicroservice.domain.api;

import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;

import java.util.List;

public interface IDishServicePort {
    void saveDish(Dish dish);
    void updateDish(Integer idDish, String description,Integer price);
    void changeDishState(Integer idDish, Boolean state);
    List<Dish> getDishes(Integer idRestaurant, Integer idCategory, Integer page);

}
