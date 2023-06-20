package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.collection.OrderCollection;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.mappers.IOrderCollectionMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.repositories.IOrderCollectionRepository;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.DishNotFoundException;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDocument;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderMongoAdapterTest {

    @Mock
    private IOrderCollectionRepository orderCollectionRepository;
    @Mock
    private IOrderCollectionMapper orderCollectionMapper;
    @InjectMocks
    private OrderMongoAdapter orderMongoAdapter;

    @Test
    void saveOrderCollection() {
        OrderDocument orderDocument = new OrderDocument(null, null, null, null, null, null, null, null, null);
        OrderCollection orderCollection = new OrderCollection();

        when(orderCollectionMapper.toOrderCollection(orderDocument)).thenReturn(orderCollection);

        orderMongoAdapter.saveOrderCollection(orderDocument);

        verify(orderCollectionMapper, times(1)).toOrderCollection(orderDocument);
    }

    @Test
    void getOrderCollection() {
        Long idOrder = 1L;
        OrderDocument orderDocument = new OrderDocument(null, null, null, null, null, null, null, null, null);
        OrderCollection orderCollection = new OrderCollection();

        when(orderCollectionRepository.findByIdOrder(idOrder)).thenReturn(Optional.of(orderCollection));
        when(orderCollectionMapper.toOrderDocument(orderCollection)).thenReturn(orderDocument);

        orderMongoAdapter.getOrderCollection(idOrder);

        verify(orderCollectionRepository, times(1)).findByIdOrder(idOrder);
        verify(orderCollectionMapper, times(1)).toOrderDocument(orderCollection);
    }

    @Test
    void getOrderCollection_Conflict() {
        Long idOrder = 1L;
        when(orderCollectionRepository.findByIdOrder(idOrder)).thenReturn(Optional.empty());

        assertThrows(DishNotFoundException.class, () -> orderMongoAdapter.getOrderCollection(idOrder));

        verify(orderCollectionRepository, times(1)).findByIdOrder(idOrder);
    }

    @Test
    void getOrderCollections() {
        Long idClient = 1L;
        OrderDocument orderDocument = new OrderDocument(null, null, null, null, null, null, null, null, null);
        OrderCollection orderCollection = new OrderCollection();

        when(orderCollectionRepository.findByIdClient(idClient)).thenReturn(List.of(orderCollection));
        when(orderCollectionMapper.toOrderDocumentList(List.of(orderCollection))).thenReturn(List.of(orderDocument));

        orderMongoAdapter.getOrderCollections(idClient);

        verify(orderCollectionRepository, times(1)).findByIdClient(idClient);
        verify(orderCollectionMapper, times(1)).toOrderDocumentList(List.of(orderCollection));
    }

    @Test
    void getOrderCollections_Conflict() {
        Long idClient = 1L;
        List list = new ArrayList<>();
        when(orderCollectionRepository.findByIdClient(idClient)).thenReturn(list);

        assertThrows(DishNotFoundException.class, () -> orderMongoAdapter.getOrderCollections(idClient));

        verify(orderCollectionRepository, times(1)).findByIdClient(idClient);
    }

    @Test
    void assignOrderCollection() {
        Long idOrder = 1L;
        Long idEmployee = 1L;
        Long newStatus = 1L;
        OrderCollection orderCollection = new OrderCollection();

        when(orderCollectionRepository.findByIdOrder(idOrder)).thenReturn(Optional.of(orderCollection));

        orderMongoAdapter.assignOrderCollection(idOrder, idEmployee, newStatus);

        verify(orderCollectionRepository, times(1)).findByIdOrder(idOrder);
    }

    @Test
    void assignOrderCollection_Conflict() {
        Long idOrder = 1L;
        Long idEmployee = 1L;
        Long newStatus = 1L;

        when(orderCollectionRepository.findByIdOrder(idOrder)).thenReturn(Optional.empty());

        assertThrows(DishNotFoundException.class, () -> orderMongoAdapter.assignOrderCollection(idOrder, idEmployee, newStatus));

        verify(orderCollectionRepository, times(1)).findByIdOrder(idOrder);
    }

    @Test
    void changeOrderCollectionStatus() {
        Long idOrder = 1L;
        Long newStatus = 1L;
        Date finalDate = new Date();
        OrderCollection orderCollection = new OrderCollection();

        when(orderCollectionRepository.findByIdOrder(idOrder)).thenReturn(Optional.of(orderCollection));

        orderMongoAdapter.changeOrderCollectionStatus(idOrder,newStatus,finalDate);

        verify(orderCollectionRepository,times(1)).findByIdOrder(idOrder);
    }

    @Test
    void changeOrderCollectionStatus_Conflict() {
        Long idOrder = 1L;
        Long newStatus = 1L;
        Date finalDate = new Date();
        when(orderCollectionRepository.findByIdOrder(idOrder)).thenReturn(Optional.empty());

        assertThrows(DishNotFoundException.class, () -> orderMongoAdapter.changeOrderCollectionStatus(idOrder, newStatus,finalDate));

        verify(orderCollectionRepository, times(1)).findByIdOrder(idOrder);
    }

    @Test
    void updateOrderCollectionStatus() {
        Long idOrder = 1L;
        Long newStatus = 1L;
        OrderCollection orderCollection = new OrderCollection();

        when(orderCollectionRepository.findByIdOrder(idOrder)).thenReturn(Optional.of(orderCollection));

        orderMongoAdapter.updateOrderCollectionStatus(idOrder, newStatus);

        verify(orderCollectionRepository, times(1)).findByIdOrder(idOrder);
    }

    @Test
    void updateOrderCollectionStatus_Conflict() {
        Long idOrder = 1L;
        Long newStatus = 1L;
        when(orderCollectionRepository.findByIdOrder(idOrder)).thenReturn(Optional.empty());

        assertThrows(DishNotFoundException.class, () -> orderMongoAdapter.updateOrderCollectionStatus(idOrder, newStatus));

        verify(orderCollectionRepository, times(1)).findByIdOrder(idOrder);
    }
}