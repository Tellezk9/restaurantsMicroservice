package com.pragma.powerup.restaurantmicroservice.domain.spi.mongo;

import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDocument;

import java.util.Date;
import java.util.List;

public interface IOrderCollectionPersistencePort {
    void saveOrderCollection(OrderDocument orderDocument);
    OrderDocument getOrderCollectionByIdOrder(Long idOrder);
    List<OrderDocument> getOrderCollectionsByIdClient(Long idClient);
    void assignOrderCollection(Long idOrder, Long idEmployee, Long newStatus);
    void changeOrderCollectionStatus(Long idOrder, Long newStatus, Date finalDate);
    void updateOrderCollectionStatus(Long idOrder, Long newStatus);
    List<OrderDocument> getOrdersDuration(Long idRestaurant);
    List<OrderDocument> getOrdersByIdRestaurantOrderedByIdEmployee(Long idRestaurant);
}
