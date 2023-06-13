package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.RestaurantAlreadyExistsException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
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
        Integer page = 2;
        String[] restaurant1 = {"1", "testName", "testUrl"};
        String[] restaurant2 = {"2", "testName2", "testUrl2"};
        List<String[]> restaurantList = new ArrayList<>();
        restaurantList.add(restaurant1);
        restaurantList.add(restaurant2);
        Pageable pageable = PageRequest.of(page,Constants.MAX_PAGE_SIZE);

        when(restaurantRepository.getNameAndUrl(pageable)).thenReturn(restaurantList);

        restaurantMysqlAdapter.getRestaurants(page);

        verify(restaurantRepository, times(1)).getNameAndUrl(pageable);
    }

    @Test
    void getRestaurantsConflict() {
        Integer page = 2;
        Pageable pageable = PageRequest.of(page,Constants.MAX_PAGE_SIZE);
        List<String[]> restaurantEntities = Arrays.asList();

        when(restaurantRepository.getNameAndUrl(pageable)).thenReturn(restaurantEntities);

        assertThrows(NoDataFoundException.class, () -> restaurantMysqlAdapter.getRestaurants(page));
        verify(restaurantRepository, times(1)).getNameAndUrl(pageable);
    }
}