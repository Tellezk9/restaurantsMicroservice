package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.ClientHasPendingOrderException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.OrderNotFoundException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers.IOrderEntityMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IOrderPersistencePort;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class OrderMysqlAdapter implements IOrderPersistencePort {
    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    @Override
    public void saveOrderInformation(Order order) {
        Optional<OrderEntity> orderEntity = orderRepository.findByIdClientAndRestaurantEntityIdAndStatusNot(order.getIdClient(),order.getIdRestaurant().getId(), Constants.ORDER_STATUS_OK);
        if (orderEntity.isPresent()){
            throw new ClientHasPendingOrderException();
        }

        orderRepository.save(orderEntityMapper.toOrderEntity(order));
    }

    @Override
    public Order findOrderInformation(Order order) {
        Optional<OrderEntity> orderEntity = orderRepository.findByIdClientAndRestaurantEntityIdAndStatus(order.getIdClient(),order.getIdRestaurant().getId(), order.getStatus());
        if (!orderEntity.isPresent()){
            throw new OrderNotFoundException();
        }

        return orderEntityMapper.toOrder(orderEntity.get());
    }
}
