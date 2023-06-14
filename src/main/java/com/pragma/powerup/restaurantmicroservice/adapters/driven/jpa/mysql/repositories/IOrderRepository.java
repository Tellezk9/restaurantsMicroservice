package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByIdClientAndRestaurantEntityIdAndStatusNot(Long idClient,Long idRestaurant, Long status);
    Optional<OrderEntity> findByIdClientAndRestaurantEntityIdAndStatus(Long idClient,Long idRestaurant, Long status);
}
