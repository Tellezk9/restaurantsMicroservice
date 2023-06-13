package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.IDishHandler;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper.IDishRequestMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper.IDishResponseMapper;
import com.pragma.powerup.restaurantmicroservice.domain.api.IDishServicePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class DishHandlerImpl implements IDishHandler {
    private final IDishRequestMapper dishRequestMapper;
    private final IDishResponseMapper dishResponseMapper;
    private final IDishServicePort dishServicePort;

    @Override
    public void saveDish(DishRequestDto dishRequestDto) {
        dishServicePort.saveDish(dishRequestMapper.toDish(dishRequestDto));
    }

    @Override
    public void updateDish(Integer idDish, String description, Integer price) {
        dishServicePort.updateDish(idDish, description, price);
    }

    @Override
    public void changeDishState(Integer idDish, Boolean state) {
        dishServicePort.changeDishState(idDish, state);
    }

    @Override
    public List<DishResponseDto> getDishes(Integer idRestaurant, Integer idCategory, Integer page) {
        return dishResponseMapper.toListDishResponseDto(dishServicePort.getDishes(idRestaurant, idCategory, page));
    }


}
