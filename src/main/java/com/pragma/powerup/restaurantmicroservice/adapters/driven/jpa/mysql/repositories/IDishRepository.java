package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {
    List<DishEntity> findByRestaurantEntityId(Long idRestaurant, Pageable pageable);
    List<DishEntity> findByRestaurantEntityIdAndCategoryEntityId(Long idRestaurant, Long idCategory, Pageable pageable);
}
