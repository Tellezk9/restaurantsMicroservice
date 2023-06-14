package com.pragma.powerup.restaurantmicroservice.domain.model;

public class OrderDish {
    private Long idOrder;
    private Long orderDish;
    private Integer amount;

    public OrderDish(Long idOrder, Long orderDish, Integer amount) {
        this.idOrder = idOrder;
        this.orderDish = orderDish;
        this.amount = amount;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public Long getOrderDish() {
        return orderDish;
    }

    public void setOrderDish(Long orderDish) {
        this.orderDish = orderDish;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
