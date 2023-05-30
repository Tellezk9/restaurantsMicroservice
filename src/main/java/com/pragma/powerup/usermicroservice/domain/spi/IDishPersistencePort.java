package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.Dish;

public interface IDishPersistencePort {
    void saveDish(Dish dish);
    void updateDish(Long idDish, String description,Integer price);
    Dish getDishById(Long id);
}
