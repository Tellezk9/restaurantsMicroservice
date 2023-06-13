package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
import com.pragma.powerup.restaurantmicroservice.domain.model.Category;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishMysqlAdapterTest {

    @Mock
    private IDishEntityMapper dishEntityMapper;
    @Mock
    private IDishRepository dishRepository;
    @InjectMocks
    private DishMysqlAdapter dishMysqlAdapter;

    @BeforeEach
    void setUp() {
    }

    @Test
    void saveDish() {
        Category category = new Category(1L, null, null);
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Dish dish = new Dish(1L, "testName", category, "descriptionTest", 100, restaurant, "http://urlTest.com/test", true);

        CategoryEntity categoryEntity = new CategoryEntity(1L, null, null);
        RestaurantEntity restaurantEntity = new RestaurantEntity(1L, null, null, null, null, null, null);
        DishEntity dishEntity = new DishEntity(1L, "testName", categoryEntity, "descriptionTest", 100, restaurantEntity, "http://urlTest.com/test", true);

        when(dishEntityMapper.toDishEntity(dish)).thenReturn(dishEntity);

        dishMysqlAdapter.saveDish(dish);

        verify(dishEntityMapper, times(1)).toDishEntity(dish);
        verify(dishRepository, times(1)).save(dishEntity);
    }

    @Test
    void updateDishSuccess() {
        Category category = new Category(1L, null, null);
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Dish dish = new Dish(1L, "testName", category, "descriptionTest", 100, restaurant, "http://urlTest.com/test", true);

        CategoryEntity categoryEntity = new CategoryEntity(1L, null, null);
        RestaurantEntity restaurantEntity = new RestaurantEntity(1L, null, null, null, null, null, null);
        DishEntity dishEntity = new DishEntity(1L, "testName", categoryEntity, "descriptionTest", 100, restaurantEntity, "http://urlTest.com/test", true);
        Optional<DishEntity> entityOptional = Optional.of(dishEntity);

        when(dishRepository.findById(dish.getId())).thenReturn(entityOptional);

        dishMysqlAdapter.updateDish(1L, "test", 5000);

        verify(dishRepository, times(1)).findById(dish.getId());
        verify(dishRepository, times(1)).save(dishEntity);
    }


    @Test
    @DisplayName("Update dish with conflict")
    void updateDishConflict() {
        when(dishRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(DishNotFoundException.class, () -> dishMysqlAdapter.updateDish(1L, "test", 5000));
    }

    @Test
    void changeDishStateSuccess() {
        Long idDish = 1L;
        Boolean state = true;
        DishEntity dishEntity = new DishEntity(1L, "testName", null, "descriptionTest", 100, null, "http://urlTest.com/test", true);
        Optional<DishEntity> entityOptional = Optional.of(dishEntity);

        when(dishRepository.findById(idDish)).thenReturn(entityOptional);

        dishMysqlAdapter.changeDishState(idDish, state);

        verify(dishRepository, times(1)).findById(idDish);
        verify(dishRepository, times(1)).save(dishEntity);
    }

    @Test
    @DisplayName("Update dish state with conflict")
    void changeDishStateConflict() {
        Long idDish = 1L;
        Boolean state = true;
        when(dishRepository.findById(idDish)).thenReturn(Optional.empty());
        assertThrows(DishNotFoundException.class, () -> dishMysqlAdapter.changeDishState(idDish, state));
    }

    @Test
    void getDishesByRestaurantId() {
        Long idRestaurant = 1L;
        int page = 2;
        Pageable pageable = PageRequest.of(page, Constants.MAX_PAGE_SIZE);
        DishEntity dishEntity = new DishEntity(1L, "test", null, "testDescription", 12345, null, "url", true);
        List<DishEntity> dishEntities = new ArrayList<>();
        dishEntities.add(dishEntity);

        when(dishRepository.findByRestaurantEntityId(idRestaurant, pageable)).thenReturn(dishEntities);

        dishMysqlAdapter.getDishesByRestaurantId(idRestaurant, page);

        verify(dishRepository, times(1)).findByRestaurantEntityId(idRestaurant, pageable);
    }

    @Test
    void getDishesByRestaurantIdConflict() {
        Long idRestaurant = 1L;
        int page = 2;
        Pageable pageable = PageRequest.of(page, Constants.MAX_PAGE_SIZE);
        List<DishEntity> dishEntities = new ArrayList<>();

        when(dishRepository.findByRestaurantEntityId(idRestaurant, pageable)).thenReturn(dishEntities);

        assertThrows(DishNotFoundException.class, () -> dishMysqlAdapter.getDishesByRestaurantId(idRestaurant, page));
        verify(dishRepository, times(1)).findByRestaurantEntityId(idRestaurant, pageable);
    }
}