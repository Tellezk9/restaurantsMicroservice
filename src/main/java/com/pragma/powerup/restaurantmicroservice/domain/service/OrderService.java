package com.pragma.powerup.restaurantmicroservice.domain.service;

import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
import com.pragma.powerup.restaurantmicroservice.domain.exceptions.*;
import com.pragma.powerup.restaurantmicroservice.domain.model.*;

import java.util.*;
import java.util.stream.Collectors;


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

    public List<Map<String, String>> getNameDishes(List<Dish> restaurantDishes, List<Long> idOrderDishes, List<Integer> amountDishes) {
        if (restaurantDishes.isEmpty() || idOrderDishes.isEmpty()) {
            throw new EmptyFieldFoundException();
        }
        List<Map<String, String>> result = new ArrayList<>();
        boolean validDish = false;
        int i = 0;

        for (Long dishId : idOrderDishes) {
            validDish = true;
            for (Dish dish : restaurantDishes) {
                Map<String, String> map = new HashMap<>();
                if (dish.getId().equals(dishId)) {
                    map.put("nameDish", dish.getName());
                    map.put("amount", amountDishes.get(i).toString());
                    result.add(map);
                    validDish = true;
                    break;
                }
            }
            if (!validDish) {
                throw new DishDoesNotBelongToTheRestaurantException();
            }
            i++;
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

        Random random = new Random();
        Integer securityPin = (int) ((random.nextInt(9999) + 100) + (random.nextInt(999999) + 1000)) / 2;

        Date date = new Date();
        Restaurant restaurant = new Restaurant(idRestaurant, null, null, null, null, null, null);
        return new Order(null, idUser, date, status, null, (long) securityPin, restaurant);
    }

    public List<OrderDish> makeNewListOrderDish(Long idOrder, List<Long> orderDishes, List<Integer> amounts) {
        if ((idOrder == null || idOrder <= 0) || orderDishes.isEmpty() || amounts.isEmpty()) {
            throw new EmptyFieldFoundException();
        }
        List<OrderDish> orderDishList = new ArrayList<>();

        for (int i = 0; i < orderDishes.size(); i++) {
            Dish dish = new Dish(orderDishes.get(0), null, null, null, null, null, null, null);
            Order order = new Order(idOrder, null, null, null, null, null, null);
            orderDishList.add(new OrderDish(order, dish, amounts.get(i)));
        }
        return orderDishList;
    }

    public boolean isValidStatus(Long status) {
        if (status == null || status <= 0) {
            throw new EmptyFieldFoundException();
        }
        if (!(status.equals(Constants.ORDER_STATUS_OK) || status.equals(Constants.ORDER_STATUS_PENDING) || status.equals(Constants.ORDER_STATUS_PREPARING))) {
            throw new InvalidStatusException();
        }

        return true;
    }


    public OrderDocument makeNewOrderDocument(Order orderInformation, List<Map<String, String>> dishesMapped) {
        Long idOrder = orderInformation.getId();
        Long idClient = orderInformation.getIdClient();
        Long idEmployee = null;
        Long idRestaurant = orderInformation.getRestaurant().getId();
        Date dateInit = orderInformation.getDate();
        Date dateEnd = null;
        Long previousStatus = null;
        Long actualStatus = orderInformation.getStatus();
        List<Map<String, String>> order = dishesMapped;
        return new OrderDocument(null, idOrder, idClient, idEmployee, idRestaurant, dateInit, dateEnd, previousStatus, actualStatus, order);
    }

    public List<RankingEmployee> calculateAverageTimesBetweenOrders(List<OrderDocument> orderDocumentList) {
        if (orderDocumentList.isEmpty()){
            return List.of(new RankingEmployee(null,null));
        }
        Long actualEmployee;
        Long previousEmployee = 0L;
        Long sumMinutes = 0L;
        int countOrders = 0;
        List<RankingEmployee> rankingEmployeesList = new ArrayList<>();
        List<Map<String, Long>> averageTimes = new ArrayList<>();

        for (int i = 0; i < orderDocumentList.size(); i++) {
            actualEmployee = orderDocumentList.get(i).getIdEmployee();
            Date initDate = orderDocumentList.get(i).getDateInit();
            Date endDate = orderDocumentList.get(i).getDateEnd();

            if (previousEmployee != 0L && (!actualEmployee.equals(previousEmployee))) {
                Map<String, Long> times = new HashMap<>();
                Long average = sumMinutes / countOrders;
                times.put("average", average);
                times.put("idEmployee", previousEmployee);
                averageTimes.add(times);
                sumMinutes = 0L;
                countOrders = 0;
            }
            sumMinutes += ((endDate.getTime() - initDate.getTime()) / 1000) / 60;
            countOrders++;
            previousEmployee = actualEmployee;
        }
        if (previousEmployee != null && countOrders > 0) {
            Long averageMinutes = sumMinutes / countOrders;
            Map<String, Long> times = new HashMap<>();
            times.put("average", averageMinutes);
            times.put("idEmployee", previousEmployee);
            averageTimes.add(times);
        }

        rankingEmployeesList = averageTimes.stream()
                .map(time -> new RankingEmployee(time.get("idEmployee"), time.get("average")))
                .sorted(Comparator.comparingLong(RankingEmployee::getAverage))
                .collect(Collectors.toList());

        return rankingEmployeesList;
    }
}
