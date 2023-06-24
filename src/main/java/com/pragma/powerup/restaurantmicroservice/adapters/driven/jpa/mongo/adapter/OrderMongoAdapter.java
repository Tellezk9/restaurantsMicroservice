package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.collection.OrderCollection;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.mappers.IOrderCollectionMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.repositories.IOrderCollectionRepository;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.OrderNotFoundException;
import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDocument;
import com.pragma.powerup.restaurantmicroservice.domain.spi.mongo.IOrderCollectionPersistencePort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class OrderMongoAdapter implements IOrderCollectionPersistencePort {
    private final IOrderCollectionRepository orderCollectionRepository;
    private final IOrderCollectionMapper orderCollectionMapper;

    @Override
    public void saveOrderCollection(OrderDocument orderDocument) {
        orderCollectionRepository.save(orderCollectionMapper.toOrderCollection(orderDocument));
    }

    @Override
    public OrderDocument getOrderCollectionByIdOrder(Long idOrder) {
        Optional<OrderCollection> orderCollection = orderCollectionRepository.findByIdOrder(idOrder);

        if (orderCollection.isEmpty()) {
            throw new OrderNotFoundException();
        }

        return orderCollectionMapper.toOrderDocument(orderCollection.get());
    }

    @Override
    public List<OrderDocument> getOrderCollectionsByIdClient(Long idClient) {
        List<OrderCollection> orderCollection = orderCollectionRepository.findByIdClient(idClient);

        if (orderCollection.isEmpty()) {
            throw new OrderNotFoundException();
        }

        return orderCollectionMapper.toOrderDocumentList(orderCollection);
    }

    @Override
    public void assignOrderCollection(Long idOrder, Long idEmployee, Long newStatus) {
        Optional<OrderCollection> orderCollection = orderCollectionRepository.findByIdOrder(idOrder);

        if (orderCollection.isEmpty()) {
            throw new OrderNotFoundException();
        }
        orderCollection.get().setIdEmployee(idEmployee);
        orderCollection.get().setPreviousStatus(orderCollection.get().getActualStatus());
        orderCollection.get().setActualStatus(newStatus);

        orderCollectionRepository.save(orderCollection.get());
    }

    @Override
    public void changeOrderCollectionStatus(Long idOrder, Long newStatus, Date finalDate) {
        Optional<OrderCollection> orderCollection = orderCollectionRepository.findByIdOrder(idOrder);

        if (orderCollection.isEmpty()) {
            throw new OrderNotFoundException();
        }
        orderCollection.get().setDateEnd(new SimpleDateFormat().format(finalDate));
        orderCollection.get().setPreviousStatus(orderCollection.get().getActualStatus());
        orderCollection.get().setActualStatus(newStatus);

        orderCollectionRepository.save(orderCollection.get());
    }

    @Override
    public void updateOrderCollectionStatus(Long idOrder, Long newStatus) {
        Optional<OrderCollection> orderCollection = orderCollectionRepository.findByIdOrder(idOrder);

        if (orderCollection.isEmpty()) {
            throw new OrderNotFoundException();
        }
        orderCollection.get().setPreviousStatus(orderCollection.get().getActualStatus());
        orderCollection.get().setActualStatus(newStatus);

        orderCollectionRepository.save(orderCollection.get());
    }

    @Override
    public List<OrderDocument> getOrdersDuration(Long idRestaurant) {
        List<OrderCollection> orderCollections = orderCollectionRepository.findByAndIdRestaurantAndActualStatusNot(idRestaurant, Constants.ORDER_STATUS_PENDING, Constants.ORDER_STATUS_PREPARING);

        if (orderCollections.isEmpty()) {
            throw new OrderNotFoundException();
        }
        return orderCollectionMapper.toOrderDocumentList(orderCollections);
    }

    @Override
    public List<OrderDocument> getOrdersByIdRestaurantOrderedByIdEmployee(Long idRestaurant) {
        Sort sort = Sort.by(Sort.Direction.ASC, "idEmployee");
        List<OrderCollection> orderCollections = orderCollectionRepository.getOrdersByIdRestaurantOrderedByIdEmployee(idRestaurant, Constants.ORDER_STATUS_PENDING, Constants.ORDER_STATUS_PREPARING, sort);

        if (orderCollections.isEmpty()) {
            throw new OrderNotFoundException();
        }
        return orderCollectionMapper.toOrderDocumentList(orderCollections);
    }
}
