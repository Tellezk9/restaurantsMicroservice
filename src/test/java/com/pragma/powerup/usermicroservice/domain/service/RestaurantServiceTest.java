package com.pragma.powerup.usermicroservice.domain.service;

import com.pragma.powerup.usermicroservice.domain.exceptions.EmptyFieldFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.InvalidRestaurantNameException;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantServiceTest {
    private RestaurantService restaurantService;

    @BeforeEach
    void setUp() {
        restaurantService = new RestaurantService();
    }

    @Test
    void allFieldsFilled() {
        Restaurant restaurant = new Restaurant(1L, "testName", "string", 4L, "+439094230412", "string", 123);
        assertTrue(() -> restaurantService.allFieldsFilled(restaurant));
    }

    @ParameterizedTest
    @CsvSource({
            ", string, 4, +439094230412, string, 123",
            "testName, , 4, +439094230412, string, 123",
            "testName, string, , +439094230412, string, 123",
            "testName, string, 4, , string, 123",
            "testName, string, 4, +439094230412, , 123",
            "testName, string, 4, +439094230412, string, ",
    })
    void allFieldsWithoutParams(String name, String address, Long idOwner, String phone, String urlLogo, Integer nit) {

        Restaurant restaurant = new Restaurant(1L, name, address, idOwner, phone, urlLogo, nit);

        assertThrows(EmptyFieldFoundException.class, () -> restaurantService.allFieldsFilled(restaurant));
    }

    @Test
    void isRestaurantNameValid() {
        assertTrue(() -> restaurantService.isRestaurantNameValid("TestName123"));
    }

    @Test
    void isRestaurantNameInValid() {
        assertThrows(InvalidRestaurantNameException.class, () -> restaurantService.isRestaurantNameValid("123"));
        assertTrue(() -> restaurantService.isRestaurantNameValid("asd"));
    }

    @Test
    void mapToRestaurantList() {

        List<String[]> restaurantsStringList = new ArrayList<>();
        String[] restaurant1 = {"1", "testName", "testUrl"};
        String[] restaurant2 = {"2", "testName2", "testUrl2"};
        restaurantsStringList.add(restaurant1);
        restaurantsStringList.add(restaurant2);

        List<Restaurant> resultMap = restaurantService.mapToRestaurantList(restaurantsStringList);

        assertEquals(2, resultMap.size());

        Restaurant restaurantResult1 = resultMap.get(0);
        assertEquals(1L, restaurantResult1.getId());
        assertEquals("testName", restaurantResult1.getName());
        assertEquals("testUrl", restaurantResult1.getUrlLogo());

        Restaurant restaurantResult2 = resultMap.get(1);
        assertEquals(2L, restaurantResult2.getId());
        assertEquals("testName2", restaurantResult2.getName());
        assertEquals("testUrl2", restaurantResult2.getUrlLogo());
    }
}