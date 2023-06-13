package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
import com.pragma.powerup.restaurantmicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.auth.IPrincipalUser;
import com.pragma.powerup.restaurantmicroservice.domain.geteway.IHttpAdapter;
import com.pragma.powerup.restaurantmicroservice.domain.model.Employee;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import com.pragma.powerup.restaurantmicroservice.domain.service.RestaurantService;
import com.pragma.powerup.restaurantmicroservice.domain.service.Validator;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IEmployeePersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestaurantPersistencePort;

import java.util.List;

public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IEmployeePersistencePort employeePersistencePort;
    public final Validator validator;
    public final RestaurantService restaurantService;
    public final IPrincipalUser authUser;
    public final IHttpAdapter httpAdapter;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IEmployeePersistencePort employeePersistencePort, IPrincipalUser authUser, IHttpAdapter httpAdapter) {
        this.restaurantService = new RestaurantService();
        this.validator = new Validator();
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.employeePersistencePort = employeePersistencePort;
        this.authUser = authUser;
        this.httpAdapter = httpAdapter;
    }

    public void saveRestaurant(Restaurant restaurant) {
        validator.hasRoleValid(authUser.getRole(), Constants.ADMIN_ROLE_NAME);

        httpAdapter.getOwner(restaurant.getIdOwner(), authUser.getToken());

        restaurantService.allFieldsFilled(restaurant);
        restaurantService.isRestaurantNameValid(restaurant.getName());

        validator.isValidPhone(restaurant.getPhone());
        validator.isValidUrl(restaurant.getUrlLogo());

        restaurantPersistencePort.saveRestaurant(restaurant);
    }

    public List<Restaurant> getRestaurants(Integer page) {
        return restaurantService.mapToRestaurantList(restaurantPersistencePort.getRestaurants(page));
    }

    @Override
    public Restaurant getRestaurant(Integer idOwner, Integer idRestaurant) {
        return restaurantPersistencePort.getRestaurantByIdOwnerAndIdRestaurant(Long.valueOf(idOwner), Long.valueOf(idRestaurant));
    }

    public List<Restaurant> getOwnerRestaurants(Integer idOwner) {
        validator.hasRoleValid(authUser.getRole(), Constants.OWNER_ROLE_NAME);

        return restaurantPersistencePort.getOwnerRestaurants(Long.valueOf(String.valueOf(idOwner)));
    }

    public void saveRestaurantEmployee(Employee employee) {
        validator.hasRoleValid(authUser.getRole(), Constants.OWNER_ROLE_NAME);

        validator.isIdValid(Integer.valueOf(Long.toString(employee.getIdEmployee())));
        validator.isIdValid(Integer.valueOf(Long.toString(employee.getIdRestaurant().getId())));

        restaurantPersistencePort.getRestaurantByIdOwnerAndIdRestaurant(authUser.getIdUser(), employee.getIdRestaurant().getId());

        employeePersistencePort.saveEmployee(employee);
    }

}
