package com.pragma.powerup.restaurantmicroservice.domain.spi.mySql;

import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;

import java.util.List;

public interface IDishPersistencePort {
    void saveDish(Dish dish);
    void updateDish(Long idDish, String description,Integer price);
    Dish getDishById(Long id);
    void changeDishState(Long idDish, Boolean state);
    List<Dish> getDishesByRestaurantIdPageable(Long idRestaurant,Integer page);
    List<Dish> getDishesByRestaurantId(Long idRestaurant);
    List<Dish> getDishesByRestaurantIdAndCategoryIdPageable(Long idRestaurant,Long idCategory,Integer page);
}
