package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@Getter
@Setter
public class OrderPk implements Serializable {
    @Column(name = "id_order")
    private Long orderId;
    @Column(name = "id_dish")
    private Long dishId;
}
