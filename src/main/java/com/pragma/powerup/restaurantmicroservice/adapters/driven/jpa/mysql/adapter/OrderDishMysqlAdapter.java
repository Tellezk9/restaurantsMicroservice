package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers.IOrderDishEntityMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories.IOrderDishRepository;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDish;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IOrderDishPersistencePort;
import lombok.AllArgsConstructor;

import java.util.List;


@AllArgsConstructor
public class OrderDishMysqlAdapter implements IOrderDishPersistencePort {
    private final IOrderDishRepository orderDishRepository;
    private final IOrderDishEntityMapper orderDishEntityMapper;

    @Override
    public void saveOrderDish(List<OrderDish> orderDish) {
        orderDishRepository.saveAll(orderDishEntityMapper.toListOrderDishEntity(orderDish));
    }
}
