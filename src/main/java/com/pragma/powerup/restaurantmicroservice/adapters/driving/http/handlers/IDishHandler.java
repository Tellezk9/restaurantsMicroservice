package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.DishResponseDto;

import java.util.List;

public interface IDishHandler {
    void saveDish(DishRequestDto dishRequestDto);
    void updateDish(Integer idDish, String description,Integer price);
    void changeDishState(Integer idDish, Boolean state);
    List<DishResponseDto> getDishes(Integer idRestaurant, Integer idCategory, Integer page);
}
