package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderDishRepository extends JpaRepository<OrderDishEntity, Long> {
    List<OrderDishEntity> findAllByOrderEntityId(Long idOrder);
    List<OrderDishEntity> findAllByOrderEntityRestaurantEntityIdAndOrderEntityStatus(Long idRestaurant, Long status, Pageable pageable);
}
