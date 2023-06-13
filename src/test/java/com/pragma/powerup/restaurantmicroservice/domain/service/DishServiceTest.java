package com.pragma.powerup.restaurantmicroservice.domain.service;

import com.pragma.powerup.restaurantmicroservice.domain.exceptions.EmptyFieldFoundException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.InvalidPriceException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.NotOwnerTheRestaurantException;
import com.pragma.powerup.restaurantmicroservice.domain.model.Category;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DishServiceTest {
    private DishService dishService;

    @BeforeEach
    void setUp() {
        dishService = new DishService();
    }

    @Test
    void allFieldsFilled() {
        Category category = new Category(1L, null, null);
        Restaurant restaurant = new Restaurant(1L, null, null, 1L, null, null, null);
        Dish dish = new Dish(1L, "testName", category, "descriptionTest", 100, restaurant, "http://urlTest.com/test", null);

        assertTrue(() -> dishService.allFieldsFilled(dish));
    }

    @Test
    void isValidPrice() {
        assertTrue(() -> dishService.isValidPrice(1234));
    }

    @Test
    void isInvalidPrice() {
        assertThrows(InvalidPriceException.class, () -> dishService.isValidPrice(null));
        assertThrows(InvalidPriceException.class, () -> dishService.isValidPrice(0));
        assertThrows(InvalidPriceException.class, () -> dishService.isValidPrice(-1));
    }

    @Test
    void isTheRestaurantOwner() {
        assertTrue(() -> dishService.isTheRestaurantOwner(1L, 1L));
    }

    @Test
    void isNotTheRestaurantOwner() {
        assertThrows(EmptyFieldFoundException.class, () -> dishService.isTheRestaurantOwner(null, null));
        assertThrows(EmptyFieldFoundException.class, () -> dishService.isTheRestaurantOwner(0L, 0L));
        assertThrows(NotOwnerTheRestaurantException.class, () -> dishService.isTheRestaurantOwner(1L, 2L));
    }
}