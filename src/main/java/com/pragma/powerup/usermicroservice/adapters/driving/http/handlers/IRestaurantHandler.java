package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;

import java.util.List;

public interface IRestaurantHandler {
    void saveRestaurant(RestaurantRequestDto restaurantRequestDto);
    RestaurantResponseDto getRestaurant(Integer idOwner, Integer idRestaurant);
    List<RestaurantResponseDto> getRestaurants();
    List<RestaurantResponseDto> getOwnerRestaurants(Integer idOwner);

}
