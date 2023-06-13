package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.GetRestaurantsResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.IRestaurantHandler;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper.IRestaurantResponseMapper;
import com.pragma.powerup.restaurantmicroservice.domain.api.IRestaurantServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantHandlerImpl implements IRestaurantHandler {
    private final IRestaurantRequestMapper restaurantRequestMapper;
    private final IRestaurantServicePort restaurantServicePort;
    private final IRestaurantResponseMapper restaurantResponseMapper;

    @Override
    public void saveRestaurant(RestaurantRequestDto restaurantRequestDto) {
       restaurantServicePort.saveRestaurant(restaurantRequestMapper.toRestaurant(restaurantRequestDto));
    }

    @Override
    public RestaurantResponseDto getRestaurant(Integer idOwner, Integer idRestaurant) {
        return restaurantResponseMapper.toRestaurantResponseDto(restaurantServicePort.getRestaurant(idOwner,idRestaurant));
    }

    @Override
    public List<GetRestaurantsResponseDto> getRestaurants(Integer page) {
        return restaurantResponseMapper.toGetRestaurantResponse(restaurantServicePort.getRestaurants(page));
    }

    @Override
    public List<RestaurantResponseDto> getOwnerRestaurants(Integer idOwner) {
        return restaurantResponseMapper.toListRestaurantResponseDto(restaurantServicePort.getOwnerRestaurants(idOwner));
    }

}
