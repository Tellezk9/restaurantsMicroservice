package com.pragma.powerup.restaurantmicroservice.domain.service;

import com.pragma.powerup.restaurantmicroservice.domain.exceptions.DishDoesNotBelongToTheRestaurantException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.EmptyFieldFoundException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.InvalidValueException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.OrderAndAmountIsNotEqualsException;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDish;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService();
    }

    @Test
    void restaurantHasTheDishes() {
        List<Dish> restaurantDishes = List.of(new Dish(1L, "nameTest", null, "testDescription", 10000, null, "urlTest", true));
        List<Long> idOrderDishes = List.of(1L);

        assertTrue(() -> orderService.restaurantHasTheDishes(restaurantDishes, idOrderDishes));
    }

    @Test
    void restaurantHasTheDishesEmpty() {
        List<Dish> restaurantDishes = List.of();
        List<Long> idOrderDishes = List.of();

        assertThrows(EmptyFieldFoundException.class, () -> orderService.restaurantHasTheDishes(restaurantDishes, idOrderDishes));
    }

    @Test
    void restaurantHasTheDishesConflic() {
        List<Dish> restaurantDishes = List.of(new Dish(1L, "nameTest", null, "testDescription", 10000, null, "urlTest", true));
        List<Long> idOrderDishes = List.of(2L);

        assertThrows(DishDoesNotBelongToTheRestaurantException.class, () -> orderService.restaurantHasTheDishes(restaurantDishes, idOrderDishes));
    }

    @Test
    void isOrderDishesAndAmountDishesValid() {
        List<Long> orderDishes = Arrays.asList(1L, 2L);
        List<Integer> amountDishes = Arrays.asList(1, 2);
        assertTrue(() -> orderService.isOrderDishesAndAmountDishesValid(orderDishes, amountDishes));
    }
    @Test
    void isOrderDishesAndAmountDishesValidEmpty() {
        List<Long> orderDishes = List.of();
        List<Integer> amountDishes = List.of();
        assertThrows(EmptyFieldFoundException.class, () -> orderService.isOrderDishesAndAmountDishesValid(orderDishes, amountDishes));
    }
    @Test
    void isOrderDishesAndAmountDishesInvalid() {
        List<Long> orderDishes = Arrays.asList(1L,2L);
        List<Integer> amountDishes = List.of(1);
        assertThrows(OrderAndAmountIsNotEqualsException.class, () -> orderService.isOrderDishesAndAmountDishesValid(orderDishes, amountDishes));
    }
    @Test
    void isOrderDishesAndAmountDishesInvalidZero() {
        List<Long> orderDishes = List.of(0L);
        List<Integer> amountDishes = List.of(0);
        assertThrows(InvalidValueException.class, () -> orderService.isOrderDishesAndAmountDishesValid(orderDishes, amountDishes));
    }
    @Test
    void makeNewOrderInformation() {
        Long idUser = 1L;
        Long idRestaurant =1L;
        Long status = 1L;

        Date expectedDate = new Date();
        Restaurant expectedRestaurant = new Restaurant(idRestaurant, null, null, null, null, null, null);
        Order expectedOrder = new Order(null, idUser, expectedDate, status, null, expectedRestaurant);

        Order actualOrder = orderService.makeNewOrderInformation(idUser,idRestaurant,status);

        assertEquals(expectedOrder.getIdClient(), actualOrder.getIdClient());
        assertEquals(expectedOrder.getStatus(), actualOrder.getStatus());
        assertEquals(expectedOrder.getIdRestaurant().getId(), actualOrder.getIdRestaurant().getId());
    }

    @Test
    void makeNewOrderInformationEmpty() {
        assertThrows(EmptyFieldFoundException.class,()->orderService.makeNewOrderInformation(0L,0L,0L));
    }

    @Test
    void makeNewListOrderDish() {
        Long idOrder = 1L;
        List<Long> orderDishes = List.of(1L);
        List<Integer> amountDishes = List.of(1);
        List<OrderDish> orderDishList = List.of(new OrderDish(idOrder, orderDishes.get(0), amountDishes.get(0)));
        OrderDish expectedOrderDish = orderDishList.get(0);
        List<OrderDish> actualOrder = orderService.makeNewListOrderDish(idOrder,orderDishes,amountDishes);

        assertEquals(expectedOrderDish.getIdOrder(), actualOrder.get(0).getIdOrder());
        assertEquals(expectedOrderDish.getOrderDish(), actualOrder.get(0).getOrderDish());
        assertEquals(expectedOrderDish.getAmount(), actualOrder.get(0).getAmount());

    }
    @Test
    void makeNewListOrderDishEmpty() {
        List<Long> orderDishes = List.of();
        List<Integer> amountDishes = List.of();
        assertThrows(EmptyFieldFoundException.class,()->orderService.makeNewListOrderDish(0L,orderDishes,amountDishes));
    }
}