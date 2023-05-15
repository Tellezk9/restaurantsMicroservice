package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import com.pragma.powerup.usermicroservice.domain.service.RestaurantValidator;
import com.pragma.powerup.usermicroservice.domain.service.Validator;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantPersistencePort;

public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    public void saveRestaurant(Restaurant restaurant) {
        RestaurantValidator restaurantValidator = new RestaurantValidator();
        Validator validator = new Validator();

        restaurantValidator.allFieldsFilled(restaurant);
        restaurantValidator.isRestaurantNameValid(restaurant.getName());
        validator.isValidPhone(restaurant.getPhone());


        restaurantPersistencePort.saveRestaurant(restaurant);
    }
}
