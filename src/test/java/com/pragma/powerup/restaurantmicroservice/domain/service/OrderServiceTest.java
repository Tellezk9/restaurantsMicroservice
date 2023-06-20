package com.pragma.powerup.restaurantmicroservice.domain.service;

import com.pragma.powerup.restaurantmicroservice.domain.exceptions.*;
import com.pragma.powerup.restaurantmicroservice.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

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
        List<Long> orderDishes = Arrays.asList(1L, 2L);
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
        Long idRestaurant = 1L;
        Long status = 1L;

        Date expectedDate = new Date();
        Restaurant expectedRestaurant = new Restaurant(idRestaurant, null, null, null, null, null, null);
        Order expectedOrder = new Order(null, idUser, expectedDate, status, null, null, expectedRestaurant);

        Order actualOrder = orderService.makeNewOrderInformation(idUser, idRestaurant, status);

        assertEquals(expectedOrder.getIdClient(), actualOrder.getIdClient());
        assertEquals(expectedOrder.getStatus(), actualOrder.getStatus());
        assertEquals(expectedOrder.getRestaurant().getId(), actualOrder.getRestaurant().getId());
    }

    @Test
    void makeNewOrderInformationEmpty() {
        assertThrows(EmptyFieldFoundException.class, () -> orderService.makeNewOrderInformation(0L, 0L, 0L));
    }

    @Test
    void makeNewListOrderDish() {
        Long idOrder = 1L;
        List<Long> orderDishes = List.of(1L);
        List<Integer> amountDishes = List.of(1);
        Order order = new Order(idOrder, null, null, null, null, null, null);
        Dish dish = new Dish(orderDishes.get(0), null, null, null, null, null, null, null);
        List<OrderDish> orderDishList = List.of(new OrderDish(order, dish, amountDishes.get(0)));
        OrderDish expectedOrderDish = orderDishList.get(0);
        List<OrderDish> actualOrder = orderService.makeNewListOrderDish(idOrder, orderDishes, amountDishes);

        assertEquals(expectedOrderDish.getOrder().getId(), actualOrder.get(0).getOrder().getId());
        assertEquals(expectedOrderDish.getOrderDish().getId(), actualOrder.get(0).getOrderDish().getId());
        assertEquals(expectedOrderDish.getAmount(), actualOrder.get(0).getAmount());

    }

    @Test
    void makeNewListOrderDishEmpty() {
        List<Long> orderDishes = List.of();
        List<Integer> amountDishes = List.of();
        assertThrows(EmptyFieldFoundException.class, () -> orderService.makeNewListOrderDish(0L, orderDishes, amountDishes));
    }

    @Test
    void isValidStatus() {
        Long status = 1L;
        assertTrue(() -> orderService.isValidStatus(status));
    }

    @Test
    void isValidStatus_Empty() {
        assertThrows(EmptyFieldFoundException.class, () -> orderService.isValidStatus(0L));
        assertThrows(EmptyFieldFoundException.class, () -> orderService.isValidStatus(null));
    }

    @Test
    void isValidStatus_Conflict() {
        Long status = 4L;
        assertThrows(InvalidStatusException.class, () -> orderService.isValidStatus(status));
    }

    @Test
    void getNameDishes() {
        List<Dish> restaurantDishes = List.of(new Dish(1L, "nameTest", null, "testDescription", 10000, null, "urlTest", true));
        List<Long> idOrderDishes = List.of(1L);
        List<Integer> amountDishes = List.of(1);

        Map<String, String> mapExpect = new HashMap<>();
        mapExpect.put("amount", "1");
        mapExpect.put("nameDish", "nameTest");
        List<Map<String, String>> resultExpect = new ArrayList<>();
        resultExpect.add(mapExpect);

        List<Map<String, String>> result = orderService.getNameDishes(restaurantDishes, idOrderDishes, amountDishes);

        assertEquals(resultExpect.get(0).get("amount"),result.get(0).get("amount"));
        assertEquals(resultExpect.get(0).get("nameDish"),result.get(0).get("nameDish"));

    }

    @Test
    void makeNewOrderDocument() {
        Long idOrder = 1L;

        Map<String, String> mapExpect = new HashMap<>();
        mapExpect.put("amount", "1");
        mapExpect.put("nameDish", "nameTest");
        List<Map<String, String>> dishesMapper = new ArrayList<>();
        dishesMapper.add(mapExpect);
        OrderDocument expectedOrder = new OrderDocument(null,idOrder,null,null,null,null,null,null,dishesMapper);
        Order order = new Order(idOrder, null, null, null, null, null, null);

        OrderDocument actualOrder = orderService.makeNewOrderDocument(order, dishesMapper);

        assertEquals(expectedOrder.getIdOrder(),actualOrder.getIdOrder());
        assertEquals(expectedOrder.getOrder(),actualOrder.getOrder());
    }

}