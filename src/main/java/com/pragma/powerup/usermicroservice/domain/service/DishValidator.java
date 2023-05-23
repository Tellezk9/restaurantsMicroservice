package com.pragma.powerup.usermicroservice.domain.service;

import com.pragma.powerup.usermicroservice.domain.exceptions.EmptyFieldFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.InvalidPriceException;
import com.pragma.powerup.usermicroservice.domain.model.Dish;

public class DishValidator {
    public boolean allFieldsFilled(Dish dish) {
        if (
                (dish.getName() == null || dish.getName().isEmpty()) ||
                        (dish.getCategory() == null || dish.getCategory().getId() < 0) ||
                        (dish.getDescription() == null || dish.getDescription().isEmpty()) ||
                        (dish.getPrice() == null || dish.getPrice() < 0) ||
                        (dish.getRestaurant() == null || dish.getRestaurant().getId() < 0) ||
                        (dish.getUrlImage() == null || dish.getUrlImage().isEmpty())
        ) {
            throw new EmptyFieldFoundException();
        }
        return true;
    }

    public boolean isValidPrice(Integer price) {
        if (price == null || price <= 0) {
            throw new InvalidPriceException();
        }
        return true;
    }
}
