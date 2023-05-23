package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.IDishServicePort;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.service.DishValidator;
import com.pragma.powerup.usermicroservice.domain.service.Validator;
import com.pragma.powerup.usermicroservice.domain.spi.IDishPersistencePort;

public class DishUseCase implements IDishServicePort {
    private final IDishPersistencePort dishPersistencePort;
    private final Validator validator;
    private final DishValidator dishValidator;

    public DishUseCase(IDishPersistencePort dishPersistencePort) {
        this.validator = new Validator();
        this.dishValidator = new DishValidator();
        this.dishPersistencePort = dishPersistencePort;
    }

    public void saveDish(Dish dish) {
        dishValidator.allFieldsFilled(dish);
        validator.isValidUrl(dish.getUrlImage());
        dish.setActive(true);
        dishPersistencePort.saveDish(dish);
    }

    @Override
    public void updateDish(Integer idDish, String description, Integer price) {
        dishValidator.isValidPrice(price);
        Long id = Long.valueOf(idDish);
        dishPersistencePort.updateDish(id, description, price);
    }
}
