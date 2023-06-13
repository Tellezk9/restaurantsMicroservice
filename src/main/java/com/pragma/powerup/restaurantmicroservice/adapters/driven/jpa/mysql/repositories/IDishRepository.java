package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {
    List<DishEntity> findByRestaurantEntityIdAndActive(Long idRestaurant, Boolean active, Pageable pageable);
    List<DishEntity> findByRestaurantEntityIdAndCategoryEntityIdAndActive(Long idRestaurant, Long idCategory, Boolean active, Pageable pageable);
}
