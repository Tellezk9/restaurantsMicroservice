package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantMysqlAdapterTest {
    @Mock
    private IRestaurantRepository restaurantRepository;
    @Mock
    private IRestaurantEntityMapper restaurantEntityMapper;
    @InjectMocks
    private RestaurantMysqlAdapter restaurantMysqlAdapter;

    @Test
    void saveRestaurant() {
        Restaurant restaurant = new Restaurant(1L, "testName", "string", 4L, "+439094230412", "string", 123);
        RestaurantEntity restaurantEntity = new RestaurantEntity(1L, "testName", "string", 4L, "+439094230412", "string", "1233");
        when(restaurantRepository.findByNit(Integer.toString(restaurant.getNit()))).thenReturn(Optional.empty());
        when(restaurantEntityMapper.toRestaurantEntity(restaurant)).thenReturn(restaurantEntity);

        restaurantMysqlAdapter.saveRestaurant(restaurant);

        verify(restaurantRepository).findByNit(Integer.toString(restaurant.getNit()));
        verify(restaurantEntityMapper).toRestaurantEntity(restaurant);
    }

    @Test
    void saveRestaurantFailed() {
        Restaurant restaurant = new Restaurant(1L, "testName1", "string", 4L, "+439094230412", "string", 123);
        RestaurantEntity restaurantEntity = new RestaurantEntity(1L, "testName", "string", 4L, "+439094230412", "string", "123");
        when(restaurantRepository.findByNit(Integer.toString(restaurant.getNit()))).thenReturn(Optional.of(restaurantEntity));

        assertThrows(RestaurantAlreadyExistsException.class, () -> restaurantMysqlAdapter.saveRestaurant(restaurant));

        verify(restaurantRepository).findByNit(Integer.toString(restaurant.getNit()));
    }

    @Test
    void getRestaurants() {
        List<Restaurant> restaurantList = Arrays.asList(
                new Restaurant(1L, "testName1", "string", 4L, "+439094230412", "string", 123)
        );
        List<RestaurantEntity> restaurantEntities = Arrays.asList(
                new RestaurantEntity(1L, "testName", "string", 4L, "+439094230412", "string", "123")
        );

        when(restaurantRepository.findAll()).thenReturn(restaurantEntities);
        when(restaurantEntityMapper.toRestaurantList(restaurantEntities)).thenReturn(restaurantList);

        restaurantMysqlAdapter.getRestaurants();

        verify(restaurantRepository, times(1)).findAll();
        verify(restaurantEntityMapper, times(1)).toRestaurantList(restaurantEntities);
    }

    @Test
    void getRestaurantsConflict() {
        List<RestaurantEntity> restaurantEntities = Arrays.asList();

        when(restaurantRepository.findAll()).thenReturn(restaurantEntities);

        assertThrows(NoDataFoundException.class, () -> restaurantMysqlAdapter.getRestaurants());
        verify(restaurantRepository, times(1)).findAll();
    }
}