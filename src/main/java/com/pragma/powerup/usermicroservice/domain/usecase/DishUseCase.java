package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.usermicroservice.domain.auth.IPrincipalUser;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.service.DishValidator;
import com.pragma.powerup.usermicroservice.domain.service.Validator;
import com.pragma.powerup.usermicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantPersistencePort;

public class DishUseCase implements IDishServicePort {
    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final Validator validator;
    private final DishValidator dishValidator;
    public final IPrincipalUser authUser;

    public DishUseCase(IDishPersistencePort dishPersistencePort, IPrincipalUser authUser, IRestaurantPersistencePort restaurantPersistencePort) {
        this.validator = new Validator();
        this.dishValidator = new DishValidator();
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.authUser = authUser;
    }

    public void saveDish(Dish dish) {
        validator.hasRoleValid(authUser.getRole(), Constants.OWNER_ROLE_NAME);

        dishValidator.allFieldsFilled(dish);
        validator.isValidUrl(dish.getUrlImage());
        dish.setActive(true);

        restaurantPersistencePort.getRestaurantByIdOwnerAndIdRestaurant(authUser.getIdUser(), dish.getRestaurant().getId());
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public void updateDish(Integer idDish, String description, Integer price) {
        validator.hasRoleValid(authUser.getRole(), Constants.OWNER_ROLE_NAME);

        validator.isStringFilled(description);
        validator.isIdValid(idDish);

        Long id = Long.valueOf(idDish);
        Dish dish = dishPersistencePort.getDishById(id);
        dishValidator.isTheRestaurantOwner(dish.getRestaurant().getIdOwner(), authUser.getIdUser());
        dishValidator.isValidPrice(price);

        dishPersistencePort.updateDish(id, description, price);
    }
}
