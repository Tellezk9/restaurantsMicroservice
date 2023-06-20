package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.collection.OrderCollection;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.mappers.IOrderCollectionMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.repositories.IOrderCollectionRepository;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundException;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDocument;
import com.pragma.powerup.restaurantmicroservice.domain.spi.mongo.IOrderCollectionPersistencePort;
import lombok.AllArgsConstructor;

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
        OrderCollection orderCollection = orderCollectionMapper.toOrderCollection(orderDocument);
        orderCollectionRepository.save(orderCollection);
    }

    @Override
    public OrderDocument getOrderCollection(Long idOrder) {
        Optional<OrderCollection> orderCollection = orderCollectionRepository.findByIdOrder(idOrder);

        if (orderCollection.isEmpty()){
            throw new DishNotFoundException();
        }

        return orderCollectionMapper.toOrderDocument(orderCollection.get());
    }

    @Override
    public List<OrderDocument> getOrderCollections(Long idClient) {
        List<OrderCollection> orderCollection = orderCollectionRepository.findByIdClient(idClient);

        if (orderCollection.isEmpty()){
            throw new DishNotFoundException();
        }

        return orderCollectionMapper.toOrderDocumentList(orderCollection);
    }

    @Override
    public void assignOrderCollection(Long idOrder, Long idEmployee, Long newStatus) {
        Optional<OrderCollection> orderCollection = orderCollectionRepository.findByIdOrder(idOrder);

        if (orderCollection.isEmpty()) {
            throw new DishNotFoundException();
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
            throw new DishNotFoundException();
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
            throw new DishNotFoundException();
        }
        orderCollection.get().setPreviousStatus(orderCollection.get().getActualStatus());
        orderCollection.get().setActualStatus(newStatus);

        orderCollectionRepository.save(orderCollection.get());
    }
}
