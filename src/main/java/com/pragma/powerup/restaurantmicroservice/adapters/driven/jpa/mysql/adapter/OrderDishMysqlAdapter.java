package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.OrderNotFoundException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers.IOrderDishEntityMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories.IOrderDishRepository;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDish;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IOrderDishPersistencePort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.pragma.powerup.restaurantmicroservice.configuration.Constants.MAX_PAGE_SIZE;


@AllArgsConstructor
public class OrderDishMysqlAdapter implements IOrderDishPersistencePort {
    private final IOrderDishRepository orderDishRepository;
    private final IOrderDishEntityMapper orderDishEntityMapper;

    @Override
    public void saveOrderDish(List<OrderDish> orderDish) {
        orderDishRepository.saveAll(orderDishEntityMapper.toListOrderDishEntity(orderDish));
    }

    @Override
    public List<OrderDish> getOrderDishPageable(Long status, Long idRestaurant, Integer page) {
        Pageable pagination = PageRequest.of(page, MAX_PAGE_SIZE);
        List<OrderDishEntity> orderEntityList = orderDishRepository.findAllByOrderEntityRestaurantEntityIdAndOrderEntityStatus(idRestaurant,status,pagination);
        if (orderEntityList.isEmpty()){
            throw new OrderNotFoundException();
        }
        return orderDishEntityMapper.toListOrderDish(orderEntityList);
    }

    @Override
    public List<OrderDish> getOrderDishes(Long idOrder) {
        List<OrderDishEntity> orderEntityList = orderDishRepository.findAllByOrderEntityId(idOrder);
        if (orderEntityList.isEmpty()){
            throw new OrderNotFoundException();
        }
        return orderDishEntityMapper.toListOrderDish(orderEntityList);
    }
}
