package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper.IDishRequestMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper.IDishResponseMapper;
import com.pragma.powerup.restaurantmicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.model.Category;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishHandlerImplTest {
    @Mock
    private IDishServicePort dishServicePort;
    @Mock
    private IDishRequestMapper dishRequestMapper;
    @Mock
    private IDishResponseMapper dishResponseMapper;
    @InjectMocks
    private DishHandlerImpl dishHandlerImpl;

    @Test
    void saveDish() {
        DishRequestDto dishRequestDto = new DishRequestDto("testName", 5000, "descriptionTest", "http", 1, 1);

        Category category = new Category(1L, null, null);
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Dish dish = new Dish(1L, "testName", category, "descriptionTest", 5000, restaurant, "http", true);
        when(dishRequestMapper.toDish(dishRequestDto)).thenReturn(dish);
        doNothing().when(dishServicePort).saveDish(dish);

        dishHandlerImpl.saveDish(dishRequestDto);

        verify(dishRequestMapper, times(1)).toDish(dishRequestDto);
        verify(dishServicePort, times(1)).saveDish(dish);

    }

    @Test
    void updateDish() {
        doNothing().when(dishServicePort).updateDish(1, "test", 5000);

        dishServicePort.updateDish(1, "test", 5000);

        verify(dishServicePort, times(1)).updateDish(1, "test", 5000);
    }

    @Test
    void changeDishState() {
        Integer idDish = 1;
        Boolean state = true;

        doNothing().when(dishServicePort).changeDishState(idDish, state);

        dishServicePort.changeDishState(idDish, state);

        verify(dishServicePort, times(1)).changeDishState(idDish, state);
    }

    @Test
    void getDishes() {
        Integer idRestaurant = 1;
        Integer idCategory = 1;
        Integer page = 0;
        Category category = new Category(1L, null, null);
        Dish dish = new Dish(1L,"testName",category,"testDescription",12355,null,"Testurl",true);
        DishResponseDto dishResponseDto = new DishResponseDto(1,"testName",12345,"testDescription","http://test.com",Integer.valueOf(String.valueOf(category.getId())));

        List<DishResponseDto> dishResponseDtoList = new ArrayList<>();
        dishResponseDtoList.add(dishResponseDto);
        List<Dish> dishList = new ArrayList<>();
        dishList.add(dish);

        when(dishServicePort.getDishes(idRestaurant, idCategory, page)).thenReturn(dishList);
        when(dishResponseMapper.toListDishResponseDto(dishList)).thenReturn(dishResponseDtoList);

        dishHandlerImpl.getDishes(idRestaurant,idCategory,page);

        verify(dishServicePort,times(1)).getDishes(idRestaurant,idCategory,page);
        verify(dishResponseMapper, times(1)).toListDishResponseDto(dishList);

    }
}