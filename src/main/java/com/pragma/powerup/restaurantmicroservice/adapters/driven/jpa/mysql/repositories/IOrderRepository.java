package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByIdClientAndRestaurantEntityIdAndStatusNot(Long idClient,Long idRestaurant, Long status);
    Optional<OrderEntity> findByIdClientAndRestaurantEntityIdAndStatus(Long idClient,Long idRestaurant, Long status);
    List<OrderEntity> findByRestaurantEntityIdAndStatus(Long idRestaurant, Long status, Pageable pageable);
    Optional<OrderEntity> findBySecurityPinAndRestaurantEntityIdAndStatus(Long securityCode, Long idRestaurant, Long status);
}
