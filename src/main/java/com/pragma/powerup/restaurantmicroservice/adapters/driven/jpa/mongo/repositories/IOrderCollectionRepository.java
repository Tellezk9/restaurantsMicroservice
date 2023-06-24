package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.repositories;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.collection.OrderCollection;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IOrderCollectionRepository extends MongoRepository<OrderCollection, String> {
    Optional<OrderCollection> findByIdOrder(Long idOrder);
    List<OrderCollection> findByIdClient(Long idClient);
    @Query("{$and: [{idRestaurant: ?0}, {actualStatus: {$ne: ?1}}, {actualStatus: {$ne: ?2}}]}")
    List<OrderCollection> findByAndIdRestaurantAndActualStatusNot(Long idRestaurant,Long pendingStatus, Long preparingStatus);
    @Query("{$and: [{idRestaurant: ?0}, {actualStatus: {$ne: ?1}}, {actualStatus: {$ne: ?2}}]}")
    List<OrderCollection> getOrdersByIdRestaurantOrderedByIdEmployee(Long idRestaurant, Long pendingStatus, Long preparingStatus, Sort sort);
}
