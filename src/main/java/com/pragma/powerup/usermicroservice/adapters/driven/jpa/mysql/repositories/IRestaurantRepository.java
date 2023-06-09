package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IRestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    Optional<RestaurantEntity> findByNit(String nit);

    Optional<RestaurantEntity> findByIdOwnerAndId(Long idOwner, Long idRestaurant);

    List<RestaurantEntity> findByIdOwner(Long idOwner);

    @Query("SELECT id,name,urlLogo FROM RestaurantEntity r")
    List<String[]> getNameAndUrl(Pageable pageable);
}
