package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.Dish;

public interface IDishServicePort {
    void saveDish(Dish dish);
    void updateDish(Integer idDish, String description,Integer price);
    void changeDishState(Integer idDish, Boolean state);

}
