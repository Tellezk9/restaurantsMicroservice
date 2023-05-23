package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.model.Category;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import com.pragma.powerup.usermicroservice.domain.spi.IDishPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {
    @Mock
    private IDishPersistencePort dishPersistencePort;

    @InjectMocks
    private DishUseCase dishUseCase;

    @Test
    void saveDish() {
        Category category = new Category(1L, null, null);
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Dish dish = new Dish(1l, "testName", category, "descriptionTest", 100, restaurant, "http://urlTest.com/test", null);

        doNothing().when(dishPersistencePort).saveDish(dish);

        dishUseCase.saveDish(dish);

        verify(dishPersistencePort, times(1)).saveDish(dish);
    }

    @Test
    void updateDish() {
        doNothing().when(dishPersistencePort).updateDish(1L,"testDescription",5000);

        dishUseCase.updateDish(1,"testDescription",5000);

        verify(dishPersistencePort, times(1)).updateDish(1L,"testDescription",5000);
    }
}