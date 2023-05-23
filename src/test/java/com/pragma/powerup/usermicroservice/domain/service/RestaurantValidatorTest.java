package com.pragma.powerup.usermicroservice.domain.service;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.pragma.powerup.usermicroservice.domain.exceptions.EmptyFieldFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.InvalidRestaurantNameException;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantValidatorTest {
    private RestaurantValidator restaurantValidator;

    @BeforeEach
    void setUp() {
        restaurantValidator = new RestaurantValidator();
    }

    @Test
    void allFieldsFilled() {
        Restaurant restaurant = new Restaurant("testName", "string", 4L, "+439094230412", "string", 123);
        assertTrue(() -> restaurantValidator.allFieldsFilled(restaurant));
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

        Restaurant restaurant = new Restaurant(name,address,idOwner,phone,urlLogo,nit);

        assertThrows(EmptyFieldFoundException.class, () -> restaurantValidator.allFieldsFilled(restaurant));
    }

    @Test
    void isRestaurantNameValid() {
        assertTrue(()-> restaurantValidator.isRestaurantNameValid("TestName123"));
    }

    @Test
    void isRestaurantNameInValid() {
        assertThrows(InvalidRestaurantNameException.class,()-> restaurantValidator.isRestaurantNameValid("123"));
        assertThrows(InvalidRestaurantNameException.class,()-> restaurantValidator.isRestaurantNameValid("asd"));
    }

}