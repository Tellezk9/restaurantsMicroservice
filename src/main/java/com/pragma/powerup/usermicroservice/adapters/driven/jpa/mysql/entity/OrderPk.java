package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
@Embeddable
public class OrderPk implements Serializable {
    @ManyToOne
    @JoinColumn(name = "id_order")
    private OrderEntity orderEntity;
    @ManyToOne
    @JoinColumn(name = "id_dish")
    private DishEntity dishEntity;
}
