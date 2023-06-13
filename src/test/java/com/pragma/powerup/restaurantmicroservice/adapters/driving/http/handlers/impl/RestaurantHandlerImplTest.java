package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.GetRestaurantsResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper.IRestaurantResponseMapper;
import com.pragma.powerup.restaurantmicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantHandlerImplTest {
    @Mock
    private IRestaurantServicePort restaurantServicePort;
    @Mock
    private IRestaurantResponseMapper restaurantResponseMapper;
    @Mock
    private IRestaurantRequestMapper restaurantRequestMapper;

    @InjectMocks
    private RestaurantHandlerImpl restaurantHandler;

    @Test
    void saveRestaurant() {
        RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto("testName", "address", 1, "+12345678912", "http", 123);
        Restaurant restaurant = new Restaurant(1L, "name", "address", 1L, "123", "urlLogo", 123);

        doNothing().when(restaurantServicePort).saveRestaurant(restaurant);
        when(restaurantRequestMapper.toRestaurant(restaurantRequestDto)).thenReturn(restaurant);

        restaurantHandler.saveRestaurant(restaurantRequestDto);

        verify(restaurantServicePort, times(1)).saveRestaurant(restaurant);
        verify(restaurantRequestMapper, times(1)).toRestaurant(restaurantRequestDto);
    }

    @Test
    void getRestaurants() {
        Integer page = 2;
        List<GetRestaurantsResponseDto> getRestaurantsResponseDto = List.of(
                new GetRestaurantsResponseDto(1L, "urlLogo", "testName")
        );

        List<Restaurant> restaurant = List.of(
                new Restaurant(1L, "name", "address", 1L, "123", "urlLogo", 123)
        );

        when(restaurantServicePort.getRestaurants(page)).thenReturn(restaurant);
        when(restaurantResponseMapper.toGetRestaurantResponse(restaurant)).thenReturn(getRestaurantsResponseDto);

        restaurantHandler.getRestaurants(page);

        verify(restaurantServicePort,times(1)).getRestaurants(page);
        verify(restaurantResponseMapper,times(1)).toGetRestaurantResponse(restaurant);
    }
}