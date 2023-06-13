package com.pragma.powerup.restaurantmicroservice.domain.service;

import com.pragma.powerup.restaurantmicroservice.domain.exceptions.EmptyFieldFoundException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.InvalidPriceException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.NotOwnerTheRestaurantException;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;

public class DishService {
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

    public boolean isTheRestaurantOwner(Long idOwner, Long idAuthUser) {
        if ((idOwner == null || idOwner <= 0) || (idAuthUser == null|| idAuthUser <= 0)) {
            throw new EmptyFieldFoundException();
        }
        if (!idOwner.equals(idAuthUser)) {
            throw new NotOwnerTheRestaurantException();
        }
        return true;
    }
}
