package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.usermicroservice.domain.auth.IPrincipalUser;
import com.pragma.powerup.usermicroservice.domain.geteway.IHttpAdapter;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import com.pragma.powerup.usermicroservice.domain.service.RestaurantValidator;
import com.pragma.powerup.usermicroservice.domain.service.Validator;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantPersistencePort;

import java.util.List;

public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;
    public final Validator validator;
    public final RestaurantValidator restaurantValidator;
    public final IPrincipalUser authUser;
    public final IHttpAdapter httpAdapter;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IPrincipalUser authUser, IHttpAdapter httpAdapter) {
        this.restaurantValidator = new RestaurantValidator();
        this.validator = new Validator();
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.authUser = authUser;
        this.httpAdapter = httpAdapter;
    }

    public void saveRestaurant(Restaurant restaurant) {
        validator.hasRoleValid(authUser.getRole(), Constants.ADMIN_ROLE_NAME);

        httpAdapter.getOwner(restaurant.getIdOwner(),authUser.getToken());

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
