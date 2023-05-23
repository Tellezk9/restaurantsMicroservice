package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import com.pragma.powerup.usermicroservice.domain.service.RestaurantValidator;
import com.pragma.powerup.usermicroservice.domain.service.Validator;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantPersistencePort;

import java.util.List;

public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;
    public final Validator validator;
    public final RestaurantValidator restaurantValidator;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort) {
        this.restaurantValidator = new RestaurantValidator();
        this.validator = new Validator();
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    public void saveRestaurant(Restaurant restaurant) {

        restaurantValidator.allFieldsFilled(restaurant);
        restaurantValidator.isRestaurantNameValid(restaurant.getName());
        validator.isValidPhone(restaurant.getPhone());
        validator.isValidUrl(restaurant.getUrlLogo());


        restaurantPersistencePort.saveRestaurant(restaurant);
    }

    public List<Restaurant> getRestaurants() {
        return restaurantPersistencePort.getRestaurants();
    }

}
