package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.spi.IDishPersistencePort;
import lombok.AllArgsConstructor;

import java.util.Optional;

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
        if (optionalDishEntity.isEmpty()){
            throw new NoDataFoundException();
        }
        DishEntity dishEntity = optionalDishEntity.get();
        dishEntity.setDescription(description);
        dishEntity.setPrice(price);
        dishRepository.save(dishEntity);
    }
}
