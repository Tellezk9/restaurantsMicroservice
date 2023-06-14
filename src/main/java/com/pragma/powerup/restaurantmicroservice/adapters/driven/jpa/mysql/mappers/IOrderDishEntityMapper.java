package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderDishEntityMapper {
    @Mapping(target = "orderEntity.id",source = "idOrder")
    @Mapping(target = "dishEntity.id",source = "orderDish")
    OrderDishEntity toOrderDishEntity(OrderDish orderDish);
    List<OrderDishEntity> toListOrderDishEntity(List<OrderDish> orderDishes);
}
