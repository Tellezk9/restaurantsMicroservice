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
    public boolean isRestaurantNameValid(String name){
        String pattern = "^(?=.*[0-9])(?=.*[a-zA-Z])[a-zA-Z0-9]+$";
        if (name.matches(pattern)){
            return true;
        }
        else {
            throw new InvalidRestaurantNameException();
        }
    }
}
