package com.pragma.powerup.usermicroservice.domain.service;

import com.pragma.powerup.usermicroservice.domain.exceptions.*;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;

public class RestaurantValidator {

    public boolean allFieldsFilled(Restaurant restaurant) {
        if (
                (restaurant.getName() == null || restaurant.getName().isEmpty()) ||
                        (restaurant.getAddress() == null || restaurant.getAddress().isEmpty()) ||
                        (restaurant.getIdOwner() == null || restaurant.getIdOwner() < 0) ||
                        (restaurant.getPhone() == null || restaurant.getPhone().isEmpty()) ||
                        (restaurant.getUrlLogo() == null || restaurant.getUrlLogo().isEmpty()) ||
                        (restaurant.getNit() == null || restaurant.getNit() < 0)
        ) {
            throw new EmptyFieldFoundException();
        }
        return true;
    }
    public boolean isRestaurantNameValid(String name) {
        try {
            if (Integer.valueOf(name) > 0) {
                throw new InvalidRestaurantNameException();
            }
        } catch (NumberFormatException ex) {
        }
        return true;
    }
}
