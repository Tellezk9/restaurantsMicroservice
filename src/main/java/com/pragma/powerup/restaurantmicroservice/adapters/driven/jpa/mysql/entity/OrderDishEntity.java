package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_dish")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDishEntity {
    @EmbeddedId
    private OrderPk id;
    private Integer amount;
}
