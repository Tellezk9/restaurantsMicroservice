package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.collection.OrderCollection;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.mappers.IOrderCollectionMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mongo.repositories.IOrderCollectionRepository;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.OrderNotFoundException;
import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDocument;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

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
        OrderDocument orderDocument = new OrderDocument(null, null, null, null, null, null, null, null, null, null);
        OrderCollection orderCollection = new OrderCollection();

        when(orderCollectionMapper.toOrderCollection(orderDocument)).thenReturn(orderCollection);

        orderMongoAdapter.saveOrderCollection(orderDocument);

        verify(orderCollectionMapper, times(1)).toOrderCollection(orderDocument);
    }

    @Test
    void getOrderCollection() {
        Long idOrder = 1L;
        OrderDocument orderDocument = new OrderDocument(null, null, null, null, null, null, null, null, null, null);
        OrderCollection orderCollection = new OrderCollection();

        when(orderCollectionRepository.findByIdOrder(idOrder)).thenReturn(Optional.of(orderCollection));
        when(orderCollectionMapper.toOrderDocument(orderCollection)).thenReturn(orderDocument);

        orderMongoAdapter.getOrderCollectionByIdOrder(idOrder);

        verify(orderCollectionRepository, times(1)).findByIdOrder(idOrder);
        verify(orderCollectionMapper, times(1)).toOrderDocument(orderCollection);
    }

    @Test
    void getOrderCollection_Conflict() {
        Long idOrder = 1L;
        when(orderCollectionRepository.findByIdOrder(idOrder)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderMongoAdapter.getOrderCollectionByIdOrder(idOrder));

        verify(orderCollectionRepository, times(1)).findByIdOrder(idOrder);
    }

    @Test
    void getOrderCollections() {
        Long idClient = 1L;
        OrderDocument orderDocument = new OrderDocument(null, null, null, null, null, null, null, null, null, null);
        OrderCollection orderCollection = new OrderCollection();

        when(orderCollectionRepository.findByIdClient(idClient)).thenReturn(List.of(orderCollection));
        when(orderCollectionMapper.toOrderDocumentList(List.of(orderCollection))).thenReturn(List.of(orderDocument));

        orderMongoAdapter.getOrderCollectionsByIdClient(idClient);

        verify(orderCollectionRepository, times(1)).findByIdClient(idClient);
        verify(orderCollectionMapper, times(1)).toOrderDocumentList(List.of(orderCollection));
    }

    @Test
    void getOrderCollections_Conflict() {
        Long idClient = 1L;
        when(orderCollectionRepository.findByIdClient(idClient)).thenReturn(List.of());

        assertThrows(OrderNotFoundException.class, () -> orderMongoAdapter.getOrderCollectionsByIdClient(idClient));

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

        assertThrows(OrderNotFoundException.class, () -> orderMongoAdapter.assignOrderCollection(idOrder, idEmployee, newStatus));

        verify(orderCollectionRepository, times(1)).findByIdOrder(idOrder);
    }

    @Test
    void changeOrderCollectionStatus() {
        Long idOrder = 1L;
        Long newStatus = 1L;
        Date finalDate = new Date();
        OrderCollection orderCollection = new OrderCollection();

        when(orderCollectionRepository.findByIdOrder(idOrder)).thenReturn(Optional.of(orderCollection));

        orderMongoAdapter.changeOrderCollectionStatus(idOrder, newStatus, finalDate);

        verify(orderCollectionRepository, times(1)).findByIdOrder(idOrder);
    }

    @Test
    void changeOrderCollectionStatus_Conflict() {
        Long idOrder = 1L;
        Long newStatus = 1L;
        Date finalDate = new Date();
        when(orderCollectionRepository.findByIdOrder(idOrder)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderMongoAdapter.changeOrderCollectionStatus(idOrder, newStatus, finalDate));

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

        assertThrows(OrderNotFoundException.class, () -> orderMongoAdapter.updateOrderCollectionStatus(idOrder, newStatus));

        verify(orderCollectionRepository, times(1)).findByIdOrder(idOrder);
    }

    @Test
    void getOrdersDuration() {
        Long idRestaurant = 1L;
        OrderCollection orderCollection = new OrderCollection();
        OrderDocument orderDocument = new OrderDocument(null,null,null,null,null,null,null,null,null,null);

        when(orderCollectionRepository.findByAndIdRestaurantAndActualStatusNot(idRestaurant, Constants.ORDER_STATUS_PENDING, Constants.ORDER_STATUS_PREPARING)).thenReturn(List.of(orderCollection));
        when(orderCollectionMapper.toOrderDocumentList(List.of(orderCollection))).thenReturn(List.of(orderDocument));

        orderMongoAdapter.getOrdersDuration(idRestaurant);

        verify(orderCollectionRepository, times(1)).findByAndIdRestaurantAndActualStatusNot(idRestaurant, Constants.ORDER_STATUS_PENDING, Constants.ORDER_STATUS_PREPARING);
        verify(orderCollectionMapper, times(1)).toOrderDocumentList(List.of(orderCollection));
    }

    @Test
    void getOrdersDuration_Conflict() {
        Long idRestaurant = 1L;
        when(orderCollectionRepository.findByAndIdRestaurantAndActualStatusNot(idRestaurant, Constants.ORDER_STATUS_PENDING, Constants.ORDER_STATUS_PREPARING)).thenReturn(List.of());

        assertThrows(OrderNotFoundException.class, () -> orderMongoAdapter.getOrdersDuration(idRestaurant));

        verify(orderCollectionRepository, times(1)).findByAndIdRestaurantAndActualStatusNot(idRestaurant, Constants.ORDER_STATUS_PENDING, Constants.ORDER_STATUS_PREPARING);
    }


    @Test
    void getOrdersByIdRestaurantOrderedByIdEmployee() {
        Long idRestaurant = 1L;
        Sort sort = Sort.by(Sort.Direction.ASC, "idEmployee");
        OrderCollection orderCollection = new OrderCollection();
        OrderDocument orderDocument = new OrderDocument(null,null,null,null,null,null,null,null,null,null);

        when(orderCollectionRepository.getOrdersByIdRestaurantOrderedByIdEmployee(idRestaurant, Constants.ORDER_STATUS_PENDING, Constants.ORDER_STATUS_PREPARING, sort)).thenReturn(List.of(orderCollection));
        when(orderCollectionMapper.toOrderDocumentList(List.of(orderCollection))).thenReturn(List.of(orderDocument));

        orderMongoAdapter.getOrdersByIdRestaurantOrderedByIdEmployee(idRestaurant);

        verify(orderCollectionRepository, times(1)).getOrdersByIdRestaurantOrderedByIdEmployee(idRestaurant, Constants.ORDER_STATUS_PENDING, Constants.ORDER_STATUS_PREPARING, sort);
        verify(orderCollectionMapper, times(1)).toOrderDocumentList(List.of(orderCollection));
    }

    @Test
    void getOrdersByIdRestaurantOrderedByIdEmployee_Conflict() {
        Long idRestaurant = 1L;
        Sort sort = Sort.by(Sort.Direction.ASC, "idEmployee");
        when(orderCollectionRepository.getOrdersByIdRestaurantOrderedByIdEmployee(idRestaurant, Constants.ORDER_STATUS_PENDING, Constants.ORDER_STATUS_PREPARING, sort)).thenReturn(List.of());

        assertThrows(OrderNotFoundException.class, () -> orderMongoAdapter.getOrdersByIdRestaurantOrderedByIdEmployee(idRestaurant));

        verify(orderCollectionRepository, times(1)).getOrdersByIdRestaurantOrderedByIdEmployee(idRestaurant, Constants.ORDER_STATUS_PENDING, Constants.ORDER_STATUS_PREPARING, sort);
    }
}