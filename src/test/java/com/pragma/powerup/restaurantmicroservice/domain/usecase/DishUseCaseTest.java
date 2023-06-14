package com.pragma.powerup.restaurantmicroservice.domain.usecase;

import com.pragma.powerup.restaurantmicroservice.domain.auth.IPrincipalUser;
import com.pragma.powerup.restaurantmicroservice.domain.model.Category;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IDishPersistencePort;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

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

        Category category = new Category(1L, null, null);
        Restaurant restaurant = new Restaurant(1L, null, null, 1L, null, null, null);
        Dish dish = new Dish(1L, "testName", category, "descriptionTest", 100, restaurant, "http://urlTest.com/test", null);

        doNothing().when(dishPersistencePort).saveDish(dish);
        when(authUser.getRole()).thenReturn("ROLE_OWNER");
        when(authUser.getIdUser()).thenReturn(1L);
        when(restaurantPersistencePort.getRestaurantByIdOwnerAndIdRestaurant(1L, restaurant.getId())).thenReturn(restaurant);

        dishUseCase.saveDish(dish);

        verify(dishPersistencePort, times(1)).saveDish(dish);
        verify(restaurantPersistencePort, times(1)).getRestaurantByIdOwnerAndIdRestaurant(1L, restaurant.getId());
    }

    @Test
    void updateDish() {
        Category category = new Category(1L, null, null);
        Restaurant restaurant = new Restaurant(1L, null, null, 1L, null, null, null);
        Dish dish = new Dish(1L, "testName", category, "descriptionTest", 100, restaurant, "http://urlTest.com/test", null);

        doNothing().when(dishPersistencePort).updateDish(1L, "testDescription", 5000);
        when(authUser.getRole()).thenReturn("ROLE_OWNER");
        when(authUser.getIdUser()).thenReturn(1L);
        when(dishPersistencePort.getDishById(1L)).thenReturn(dish);

        dishUseCase.updateDish(1, "testDescription", 5000);

        verify(dishPersistencePort, times(1)).updateDish(1L, "testDescription", 5000);
    }

    @Test
    void changeDishState() {
        Long idDish = 1L;
        Boolean state = true;
        String role = "ROLE_OWNER";
        Long idUser = 1L;

        Restaurant restaurant = new Restaurant(1L, null, null, 1L, null, null, null);
        Dish dish = new Dish(idDish, "testName", null, "descriptionTest", 100, restaurant, "http://urlTest.com/test", null);

        doNothing().when(dishPersistencePort).changeDishState(idDish, state);
        when(authUser.getRole()).thenReturn(role);
        when(authUser.getIdUser()).thenReturn(idUser);
        when(dishPersistencePort.getDishById(idDish)).thenReturn(dish);
        when(restaurantPersistencePort.getRestaurantByIdOwnerAndIdRestaurant(idDish, dish.getRestaurant().getId())).thenReturn(restaurant);

        dishUseCase.changeDishState(Integer.valueOf(String.valueOf(idDish)), state);

        verify(dishPersistencePort, times(1)).changeDishState(idDish, state);
    }

    @Test
    void getDishes() {
        Long idRestaurant = 1L;
        Long idCategory = 0L;
        Integer page = 0;

        Category category = new Category(1L, null, null);
        Dish dish = new Dish(1L, "testNam2e", category, "testDescription", 12345, null, "urlTest", true);

        List<Dish> dishList = new ArrayList<>();
        dishList.add(dish);

        when(dishPersistencePort.getDishesByRestaurantIdPageable(idRestaurant, page)).thenReturn(dishList);

        dishUseCase.getDishes(Integer.valueOf(String.valueOf(idRestaurant)), Integer.valueOf(String.valueOf(idCategory)), page);

        verify(dishPersistencePort, times(1)).getDishesByRestaurantIdPageable(idRestaurant, page);
    }

    @Test
    void getDishesByCategory() {
        Long idRestaurant = 1L;
        Long idCategory = 1L;
        Integer page = 0;

        Category category = new Category(1L, null, null);
        Dish dish = new Dish(1L, "testNam2e", category, "testDescription", 12345, null, "urlTest", true);

        List<Dish> dishList = new ArrayList<>();
        dishList.add(dish);

        when(dishPersistencePort.getDishesByRestaurantIdAndCategoryIdPageable(idRestaurant, idCategory, page)).thenReturn(dishList);

        dishUseCase.getDishes(Integer.valueOf(String.valueOf(idRestaurant)), Integer.valueOf(String.valueOf(idCategory)), page);

        verify(dishPersistencePort, times(1)).getDishesByRestaurantIdAndCategoryIdPageable(idRestaurant, idCategory, page);
    }
}