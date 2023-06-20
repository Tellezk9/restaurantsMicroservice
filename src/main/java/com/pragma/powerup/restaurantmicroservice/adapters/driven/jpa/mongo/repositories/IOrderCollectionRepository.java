package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.repositories;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.collection.OrderCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface IOrderCollectionRepository extends MongoRepository<OrderCollection, String> {
    Optional<OrderCollection> findByIdOrder(Long idOrder);
    List<OrderCollection> findByIdClient(Long idClient);
}
