package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.domain.auth.IPrincipalUser;
import com.pragma.powerup.usermicroservice.domain.geteway.IHttpAdapter;
import com.pragma.powerup.usermicroservice.domain.model.Employee;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import com.pragma.powerup.usermicroservice.domain.service.Validator;
import com.pragma.powerup.usermicroservice.domain.spi.IEmployeePersistencePort;
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
    @Mock
    private IEmployeePersistencePort employeePersistencePort;
    @Mock
    private IPrincipalUser authUser;
    @Mock
    private IHttpAdapter httpAdapter;


    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    @Test
    void saveRestaurant() {

        Restaurant restaurant = new Restaurant(1L, "testNam2e", "string", 4L, "+439094230412", "http://www.test.com/", 123);

        doNothing().when(restaurantPersistencePort).saveRestaurant(restaurant);
        doNothing().when(httpAdapter).getOwner(restaurant.getIdOwner(), null);

        when(authUser.getRole()).thenReturn("ROLE_ADMIN");

        restaurantUseCase.saveRestaurant(restaurant);

        verify(restaurantPersistencePort).saveRestaurant(restaurant);
    }

    @Test
    void getRestaurants() {
        Restaurant restaurant = new Restaurant(1L, "testNam2e", "string", 4L, "+439094230412", "http://www.test.com/", 123);
        List<Restaurant> restaurantsList = new ArrayList<>();
        restaurantsList.add(restaurant);

        when(authUser.getRole()).thenReturn("ROLE_OWNER");
        when(restaurantPersistencePort.getRestaurants()).thenReturn(restaurantsList);

        restaurantUseCase.getRestaurants();
        verify(restaurantPersistencePort, times(1)).getRestaurants();
    }

    @Test
    void getRestaurant() {
        Long idRestaurant = 1L;
        Long idOwner = 1L;
        Restaurant restaurant = new Restaurant(1L, "testNam2e", "string", 4L, "+439094230412", "http://www.test.com/", 123);

        when(restaurantPersistencePort.getRestaurantByIdOwnerAndIdRestaurant(idRestaurant, idOwner)).thenReturn(restaurant);

        restaurantUseCase.getRestaurant(Integer.valueOf(String.valueOf(idRestaurant)), Integer.valueOf(String.valueOf(idOwner)));

        verify(restaurantPersistencePort, times(1)).getRestaurantByIdOwnerAndIdRestaurant(idRestaurant, idOwner);
    }

    @Test
    void getOwnerRestaurants() {
        Long idOwner = 1L;
        Restaurant restaurant = new Restaurant(1L, "testNam2e", "string", 4L, "+439094230412", "http://www.test.com/", 123);
        List<Restaurant> restaurantsList = new ArrayList<>();
        restaurantsList.add(restaurant);

        when(authUser.getRole()).thenReturn(Constants.OWNER_ROLE_NAME);
        when(restaurantPersistencePort.getOwnerRestaurants(idOwner)).thenReturn(restaurantsList);

        restaurantUseCase.getOwnerRestaurants(Integer.valueOf(String.valueOf(idOwner)));

        verify(restaurantPersistencePort,times(1)).getOwnerRestaurants(idOwner);
    }

    @Test
    void saveRestaurantEmployee() {
        Restaurant restaurant = new Restaurant(1L,null,null,null,null,null,null);
        Employee employee = new Employee(null,1L,restaurant);
        Long idUser = 1L;

        when(authUser.getRole()).thenReturn(Constants.OWNER_ROLE_NAME);
        when(authUser.getIdUser()).thenReturn(idUser);
        when(restaurantPersistencePort.getRestaurantByIdOwnerAndIdRestaurant(idUser,employee.getIdRestaurant().getId())).thenReturn(restaurant);
        doNothing().when(employeePersistencePort).saveEmployee(employee);

        restaurantUseCase.saveRestaurantEmployee(employee);

        verify(restaurantPersistencePort,times(1)).getRestaurantByIdOwnerAndIdRestaurant(idUser,employee.getIdRestaurant().getId());
        verify(employeePersistencePort,times(1)).saveEmployee(employee);

    }

}