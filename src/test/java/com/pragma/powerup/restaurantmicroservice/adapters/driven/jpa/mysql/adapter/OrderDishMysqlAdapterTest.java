package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers.IOrderDishEntityMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories.IOrderDishRepository;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDish;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderDishMysqlAdapterTest {
    @Mock
    private IOrderDishEntityMapper orderDishEntityMapper;
    @Mock
    private IOrderDishRepository orderDishRepository;
    @InjectMocks
    private OrderDishMysqlAdapter orderDishMysqlAdapter;

    @Test
    void saveOrderDish() {
        Long idOrder = 1L;
        Long orderDish = 1L;
        Integer amount = 1;
        List<OrderDish> orderDishList = List.of(new OrderDish(idOrder, orderDish, amount));

        OrderEntity orderEntity = new OrderEntity(idOrder,null,null,null,null,null);
        DishEntity dishEntity = new DishEntity(orderDish,null,null,null,null,null,null,null);
        List<OrderDishEntity> orderDishEntities = List.of(new OrderDishEntity(null, amount, orderEntity, dishEntity));

        when(orderDishEntityMapper.toListOrderDishEntity(orderDishList)).thenReturn(orderDishEntities);

        orderDishMysqlAdapter.saveOrderDish(orderDishList);

        verify(orderDishEntityMapper,times(1)).toListOrderDishEntity(orderDishList);
    }
}