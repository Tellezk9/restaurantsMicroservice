package com.pragma.powerup.usermicroservice.domain.service;

import com.pragma.powerup.usermicroservice.domain.exceptions.*;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantService {

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
        boolean result = false;
        try {
            if (Integer.valueOf(name) > 0) {
                throw new InvalidRestaurantNameException();
            }
        } catch (NumberFormatException ex) {
            result = true;
        }
        return result;
    }

    public List<Restaurant> mapToRestaurantList(List<String[]> restaurants) {
        if (restaurants.isEmpty()) {
            throw new NoRestaurantFoundException();
        }

        List<Restaurant> restaurantsList = new ArrayList<>();

        for (Object[] restaurant : restaurants) {
            Long id = Long.valueOf(restaurant[0].toString());
            String name = (String) restaurant[1];
            String urlLogo = (String) restaurant[2];

            Restaurant restaurantMapped = new Restaurant(id, name, null, null, null, urlLogo, null);
            restaurantsList.add(restaurantMapped);
        }
        return restaurantsList;
    }
}
