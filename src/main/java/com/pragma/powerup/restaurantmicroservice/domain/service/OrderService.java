package com.pragma.powerup.restaurantmicroservice.domain.service;

import com.pragma.powerup.restaurantmicroservice.domain.exceptions.DishDoesNotBelongToTheRestaurantException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.EmptyFieldFoundException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.InvalidValueException;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.OrderAndAmountIsNotEqualsException;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDish;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderService {

    public boolean restaurantHasTheDishes(List<Dish> restaurantDishes, List<Long> idOrderDishes) {
        if (restaurantDishes.isEmpty() || idOrderDishes.isEmpty()) {
            throw new EmptyFieldFoundException();
        }
        boolean result = false;

        for (Long dishId : idOrderDishes) {
            result = false;
            for (Dish dish : restaurantDishes) {
                if (dish.getId().equals(dishId)) {
                    result = true;
                    break;
                }
            }
            if (!result) {
                throw new DishDoesNotBelongToTheRestaurantException();
            }
        }

        return result;
    }

    public boolean isOrderDishesAndAmountDishesValid(List<Long> orderDishes, List<Integer> amountDishes) {
        if (orderDishes.isEmpty() || amountDishes.isEmpty()) {
            throw new EmptyFieldFoundException();
        }

        Integer orderDishesLength = orderDishes.size();
        if (!orderDishesLength.equals(amountDishes.size())) {
            throw new OrderAndAmountIsNotEqualsException();
        }

        for (int i = 0; i < orderDishesLength; i++) {
            if (orderDishes.get(i) <= 0 || amountDishes.get(i) <= 0) {
                throw new InvalidValueException();
            }
        }

        return true;
    }

    public Order makeNewOrderInformation(Long idUser, Long idRestaurant, Long status) {
        if ((idUser == null || idUser <= 0) || (idRestaurant == null || idRestaurant <= 0)) {
            throw new EmptyFieldFoundException();
        }

        Date date = new Date();
        Restaurant restaurant = new Restaurant(idRestaurant, null, null, null, null, null, null);
        return new Order(null, idUser, date, status, null, restaurant);
    }

    public List<OrderDish> makeNewListOrderDish(Long idOrder, List<Long> orderDishes, List<Integer> amounts) {
        if ((idOrder == null || idOrder <= 0) || orderDishes.isEmpty() || amounts.isEmpty()) {
            throw new EmptyFieldFoundException();
        }

        List<OrderDish> orderDishList = new ArrayList<>();

        for (int i = 0; i < orderDishes.size(); i++){
            Dish dish = new Dish(orderDishes.get(0),null,null,null,null,null,null,null);
            Order order = new Order(idOrder,null,null,null,null,null);
            orderDishList.add(new OrderDish(order,dish,amounts.get(i)));
        }
        return orderDishList;
    }
}
