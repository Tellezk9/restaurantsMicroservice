package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.security.TokenHolder;
import com.pragma.powerup.usermicroservice.domain.auth.IPrincipalUser;
import com.pragma.powerup.usermicroservice.domain.model.Category;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import com.pragma.powerup.usermicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantPersistencePort;
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
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private IPrincipalUser authUser;

    @InjectMocks
    private DishUseCase dishUseCase;

    @Test
    void saveDish() {
        IPrincipalUser authUserLogged = new TokenHolder();
        authUserLogged.setRole("ROLE_OWNER");
        authUserLogged.setIdUser(1L);

        Category category = new Category(1L, null, null);
        Restaurant restaurant = new Restaurant(1L, null, null, 1L, null, null, null);
        Dish dish = new Dish(1L, "testName", category, "descriptionTest", 100, restaurant, "http://urlTest.com/test", null);

        doNothing().when(dishPersistencePort).saveDish(dish);
        when(restaurantPersistencePort.getRestaurantByIdOwnerAndIdRestaurant(authUserLogged.getIdUser(), restaurant.getId())).thenReturn(restaurant);
        when(authUser.getRole()).thenReturn(authUserLogged.getRole());
        when(authUser.getIdUser()).thenReturn(authUserLogged.getIdUser());

        dishUseCase.saveDish(dish);

        verify(dishPersistencePort, times(1)).saveDish(dish);
        verify(restaurantPersistencePort, times(1)).getRestaurantByIdOwnerAndIdRestaurant(authUserLogged.getIdUser(), restaurant.getId());
    }

    @Test
    void updateDish() {

        IPrincipalUser authUserLogged = new TokenHolder();
        authUserLogged.setRole("ROLE_OWNER");
        authUserLogged.setIdUser(1L);

        Category category = new Category(1L, null, null);
        Restaurant restaurant = new Restaurant(1L, null, null, 1L, null, null, null);
        Dish dish = new Dish(1L, "testName", category, "descriptionTest", 100, restaurant, "http://urlTest.com/test", null);

        doNothing().when(dishPersistencePort).updateDish(1L, "testDescription", 5000);
        when(authUser.getRole()).thenReturn(authUserLogged.getRole());
        when(authUser.getIdUser()).thenReturn(authUserLogged.getIdUser());
        when(dishPersistencePort.getDishById(1L)).thenReturn(dish);

        dishUseCase.updateDish(1, "testDescription", 5000);

        verify(dishPersistencePort, times(1)).updateDish(1L, "testDescription", 5000);
    }
}