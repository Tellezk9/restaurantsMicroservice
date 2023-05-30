package com.pragma.powerup.usermicroservice.domain.service;

import com.pragma.powerup.usermicroservice.domain.exceptions.EmptyFieldFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.InvalidPriceException;
import com.pragma.powerup.usermicroservice.domain.exceptions.NotOwnerTheRestaurantException;
import com.pragma.powerup.usermicroservice.domain.model.Category;
import com.pragma.powerup.usermicroservice.domain.model.Dish;
import com.pragma.powerup.usermicroservice.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DishValidatorTest {
    private DishValidator dishValidator;

    @BeforeEach
    void setUp() {
        dishValidator = new DishValidator();
    }

    @Test
    void allFieldsFilled() {
        Category category = new Category(1L, null, null);
        Restaurant restaurant = new Restaurant(1L, null, null, 1L, null, null, null);
        Dish dish = new Dish(1L, "testName", category, "descriptionTest", 100, restaurant, "http://urlTest.com/test", null);

        assertTrue(() -> dishValidator.allFieldsFilled(dish));
    }

    @Test
    void isValidPrice() {
        assertTrue(() -> dishValidator.isValidPrice(1234));
    }

    @Test
    void isInvalidPrice() {
        assertThrows(InvalidPriceException.class, () -> dishValidator.isValidPrice(null));
        assertThrows(InvalidPriceException.class, () -> dishValidator.isValidPrice(0));
        assertThrows(InvalidPriceException.class, () -> dishValidator.isValidPrice(-1));
    }

    @Test
    void isTheRestaurantOwner() {
        assertTrue(() -> dishValidator.isTheRestaurantOwner(1L, 1L));
    }

    @Test
    void isNotTheRestaurantOwner() {
        assertThrows(EmptyFieldFoundException.class, () -> dishValidator.isTheRestaurantOwner(null, null));
        assertThrows(EmptyFieldFoundException.class, () -> dishValidator.isTheRestaurantOwner(0L, 0L));
        assertThrows(NotOwnerTheRestaurantException.class, () -> dishValidator.isTheRestaurantOwner(1L, 2L));
    }
}