package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
import com.pragma.powerup.restaurantmicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.auth.IPrincipalUser;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import com.pragma.powerup.restaurantmicroservice.domain.service.DishService;
import com.pragma.powerup.restaurantmicroservice.domain.service.Validator;
import com.pragma.powerup.restaurantmicroservice.domain.spi.mySql.IDishPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.mySql.IRestaurantPersistencePort;

import java.util.List;

public class DishUseCase implements IDishServicePort {
    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final Validator validator;
    private final DishService dishService;
    public final IPrincipalUser authUser;

    public DishUseCase(IDishPersistencePort dishPersistencePort, IPrincipalUser authUser, IRestaurantPersistencePort restaurantPersistencePort) {
        this.validator = new Validator();
        this.dishService = new DishService();
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.authUser = authUser;
    }

    public void saveDish(Dish dish) {
        validator.hasRoleValid(authUser.getRole(), Constants.OWNER_ROLE_NAME);

        dishService.allFieldsFilled(dish);
        validator.isValidUrl(dish.getUrlImage());
        dish.setActive(true);

        restaurantPersistencePort.getRestaurantByIdOwnerAndIdRestaurant(authUser.getIdUser(), dish.getRestaurant().getId());
        dishPersistencePort.saveDish(dish);
    }

    public void updateDish(Integer idDish, String description, Integer price) {
        validator.hasRoleValid(authUser.getRole(), Constants.OWNER_ROLE_NAME);

        validator.isStringFilled(description);
        validator.isIdValid(idDish);

        Long id = Long.valueOf(idDish);
        Dish dish = dishPersistencePort.getDishById(id);
        dishService.isTheRestaurantOwner(dish.getRestaurant().getIdOwner(), authUser.getIdUser());
        dishService.isValidPrice(price);

        dishPersistencePort.updateDish(id, description, price);
    }

    public void changeDishState(Integer idDish, Boolean state) {
        validator.hasRoleValid(authUser.getRole(), Constants.OWNER_ROLE_NAME);
        validator.isIdValid(idDish);

        Dish dish = dishPersistencePort.getDishById(Long.valueOf(idDish));

        restaurantPersistencePort.getRestaurantByIdOwnerAndIdRestaurant(authUser.getIdUser(), dish.getRestaurant().getId());

        dishPersistencePort.changeDishState(Long.valueOf(idDish), state);
    }

    @Override
    public List<Dish> getDishes(Integer idRestaurant, Integer idCategory, Integer page) {
        validator.isIdValid(idRestaurant);
        if (idCategory > 0) {
            return dishPersistencePort.getDishesByRestaurantIdAndCategoryIdPageable(Long.valueOf(idRestaurant), Long.valueOf(idCategory),page);
        }
        return dishPersistencePort.getDishesByRestaurantIdPageable(Long.valueOf(idRestaurant),page);
    }
}
