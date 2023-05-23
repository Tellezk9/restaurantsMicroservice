package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IRestaurantRequestMapper;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IRestaurantResponseMapper;
import com.pragma.powerup.usermicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
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
        List<RestaurantResponseDto> restaurantResponseDto = Arrays.asList(
                new RestaurantResponseDto(1L, "testName", "address", 1, "+12345678912", "http", 123)
        );

        List<Restaurant> restaurant = Arrays.asList(
                new Restaurant(1L, "name", "address", 1L, "123", "urlLogo", 123)
        );

        when(restaurantServicePort.getRestaurants()).thenReturn(restaurant);
        when(restaurantResponseMapper.toListRestaurantResponseDto(restaurant)).thenReturn(restaurantResponseDto);

        restaurantHandler.getRestaurants();

        verify(restaurantServicePort,times(1)).getRestaurants();
        verify(restaurantResponseMapper,times(1)).toListRestaurantResponseDto(restaurant);
    }
}