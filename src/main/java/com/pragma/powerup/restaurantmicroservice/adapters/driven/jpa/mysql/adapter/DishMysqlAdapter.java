package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IDishPersistencePort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static com.pragma.powerup.restaurantmicroservice.configuration.Constants.MAX_PAGE_SIZE;

@AllArgsConstructor
public class DishMysqlAdapter implements IDishPersistencePort {
    private final IDishEntityMapper dishEntityMapper;
    private final IDishRepository dishRepository;

    @Override
    public void saveDish(Dish dish) {
        dishRepository.save(dishEntityMapper.toDishEntity(dish));
    }

    @Override
    public void updateDish(Long idDish, String description, Integer price) {
        Optional<DishEntity> optionalDishEntity = dishRepository.findById(idDish);
        if (optionalDishEntity.isEmpty()) {
            throw new DishNotFoundException();
        }
        DishEntity dishEntity = optionalDishEntity.get();
        dishEntity.setDescription(description);
        dishEntity.setPrice(price);
        dishRepository.save(dishEntity);
    }

    @Override
    public Dish getDishById(Long id) {
        Optional<DishEntity> dishEntity = dishRepository.findById(id);
        if (!dishEntity.isPresent()) {
            throw new DishNotFoundException();
        }
        return dishEntityMapper.toDish(dishEntity.get());
    }

    @Override
    public void changeDishState(Long idDish, Boolean state) {
        Optional<DishEntity> optionalDishEntity = dishRepository.findById(idDish);
        if (!optionalDishEntity.isPresent()) {
            throw new DishNotFoundException();
        }
        DishEntity dishEntity = optionalDishEntity.get();
        dishEntity.setActive(state);
        dishRepository.save(dishEntity);
    }

    @Override
    public List<Dish> getDishesByRestaurantId(Long idRestaurant, Integer page) {
        Pageable pagination = PageRequest.of(page, MAX_PAGE_SIZE);
        List<DishEntity> dishEntities = dishRepository.findByRestaurantEntityIdAndActive(idRestaurant,true, pagination);
        if (dishEntities.isEmpty()) {
            throw new DishNotFoundException();
        }
        return dishEntityMapper.toListDish(dishEntities);
    }

    public List<Dish> getDishesByRestaurantIdAndCategoryId(Long idRestaurant, Long idCategory, Integer page) {
        Pageable pagination = PageRequest.of(page, MAX_PAGE_SIZE);
        List<DishEntity> dishEntities = dishRepository.findByRestaurantEntityIdAndCategoryEntityIdAndActive(idRestaurant, idCategory,true, pagination);
        if (dishEntities.isEmpty()) {
            throw new DishNotFoundException();
        }
        return dishEntityMapper.toListDish(dishEntities);
    }
}
