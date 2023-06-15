package com.pragma.powerup.restaurantmicroservice.domain.model;

public class OrderDish {
    private Order order;
    private Dish orderDish;
    private Integer amount;

    public OrderDish(Order order, Dish orderDish, Integer amount) {
        this.order = order;
        this.orderDish = orderDish;
        this.amount = amount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Dish getOrderDish() {
        return orderDish;
    }

    public void setOrderDish(Dish orderDish) {
        this.orderDish = orderDish;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
