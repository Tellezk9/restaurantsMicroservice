package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    @Test
    void saveRestaurant() {
        Restaurant restaurant = new Restaurant(1L,"testNam2e", "string", 4L, "+439094230412", "http://www.test.com/", 123);
        doNothing().when(restaurantPersistencePort).saveRestaurant(restaurant);

        restaurantUseCase.saveRestaurant(restaurant);

        verify(restaurantPersistencePort).saveRestaurant(restaurant);
    }

    @Test
    void getRestaurants(){
        Restaurant restaurant = new Restaurant(1L,"testNam2e", "string", 4L, "+439094230412", "http://www.test.com/", 123);
        List<Restaurant> restaurantsList = new ArrayList<>();
        restaurantsList.add(restaurant);

        when(restaurantPersistencePort.getRestaurants()).thenReturn(restaurantsList);

        restaurantUseCase.getRestaurants();
        verify(restaurantPersistencePort, times(1)).getRestaurants();
    }
}