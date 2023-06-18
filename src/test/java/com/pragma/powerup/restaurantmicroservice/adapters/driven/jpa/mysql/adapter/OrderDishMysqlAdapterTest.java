package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.OrderNotFoundException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers.IOrderDishEntityMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories.IOrderDishRepository;
import com.pragma.powerup.restaurantmicroservice.configuration.Constants;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDish;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
        Order order = new Order(idOrder,null,null,null,null,null,null);
        Dish dish = new Dish(orderDish,null,null,null,null,null,null,null);
        List<OrderDish> orderDishList = List.of(new OrderDish(order, dish, amount));

        OrderEntity orderEntity = new OrderEntity(idOrder,null,null,null,null,null,null);
        DishEntity dishEntity = new DishEntity(orderDish,null,null,null,null,null,null,null);
        List<OrderDishEntity> orderDishEntities = List.of(new OrderDishEntity(null, amount, orderEntity, dishEntity));

        when(orderDishEntityMapper.toListOrderDishEntity(orderDishList)).thenReturn(orderDishEntities);

        orderDishMysqlAdapter.saveOrderDish(orderDishList);

        verify(orderDishEntityMapper,times(1)).toListOrderDishEntity(orderDishList);
    }
    @Test
    void getOrderDishPageable(){
        Long idOrder = 1L;
        Long status = 1L;
        Long idRestaurant = 1L;
        int page = 1;
        String nameDish = "testDish";
        Integer amount = 1;
        Pageable pageable = PageRequest.of(page, Constants.MAX_PAGE_SIZE);

        Order order = new Order(idOrder,null,null,null,null,null,null);
        Dish dish = new Dish(null,nameDish,null,null,null,null,null,null);
        List<OrderDish> orderDishList = List.of(new OrderDish(order,dish,amount));

        OrderEntity orderEntity = new OrderEntity();
        DishEntity dishEntity = new DishEntity();
        List<OrderDishEntity> orderDishEntities = List.of(new OrderDishEntity(null,amount,orderEntity,dishEntity));

        when(orderDishRepository.findAllByOrderEntityRestaurantEntityIdAndOrderEntityStatus(idRestaurant,status,pageable)).thenReturn(orderDishEntities);
        when(orderDishEntityMapper.toListOrderDish(orderDishEntities)).thenReturn(orderDishList);

        orderDishMysqlAdapter.getOrderDishPageable(status,idRestaurant,page);

        verify(orderDishRepository,times(1)).findAllByOrderEntityRestaurantEntityIdAndOrderEntityStatus(idRestaurant,status,pageable);
        verify(orderDishEntityMapper,times(1)).toListOrderDish(orderDishEntities);
    }

    @Test
    void getOrderDishPageable_Conflict(){
        Long status = 1L;
        Long idRestaurant = 1L;
        int page = 1;
        Pageable pageable = PageRequest.of(page, Constants.MAX_PAGE_SIZE);
        List<OrderDishEntity> orderDishEntities = List.of();

        when(orderDishRepository.findAllByOrderEntityRestaurantEntityIdAndOrderEntityStatus(idRestaurant,status,pageable)).thenReturn(orderDishEntities);

        assertThrows(OrderNotFoundException.class,()->orderDishMysqlAdapter.getOrderDishPageable(status,idRestaurant,page));
    }

    @Test
    void getOrderDishes(){
        Long idOrder = 1L;
        String nameDish = "testDish";
        Integer amount = 1;

        Order order = new Order(idOrder,null,null,null,null,null,null);
        Dish dish = new Dish(null,nameDish,null,null,null,null,null,null);
        List<OrderDish> orderDishList = List.of(new OrderDish(order,dish,amount));

        OrderEntity orderEntity = new OrderEntity();
        DishEntity dishEntity = new DishEntity();
        List<OrderDishEntity> orderDishEntities = List.of(new OrderDishEntity(null,amount,orderEntity,dishEntity));

        when(orderDishRepository.findAllByOrderEntityId(idOrder)).thenReturn(orderDishEntities);
        when(orderDishEntityMapper.toListOrderDish(orderDishEntities)).thenReturn(orderDishList);

        orderDishMysqlAdapter.getOrderDishes(idOrder);

        verify(orderDishRepository,times(1)).findAllByOrderEntityId(idOrder);
        verify(orderDishEntityMapper,times(1)).toListOrderDish(orderDishEntities);
    }


    @Test
    void getOrderDishes_Conflict(){
        Long idOrder = 1L;
        List<OrderDishEntity> orderDishEntities = List.of();

        when(orderDishRepository.findAllByOrderEntityId(idOrder)).thenReturn(orderDishEntities);

        assertThrows(OrderNotFoundException.class,()->orderDishMysqlAdapter.getOrderDishes(idOrder));
    }
}