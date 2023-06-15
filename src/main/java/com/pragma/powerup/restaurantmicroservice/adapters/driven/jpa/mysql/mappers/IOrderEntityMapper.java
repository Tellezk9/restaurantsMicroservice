package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderEntityMapper {
    @Mapping(target = "restaurantEntity.id", source = "restaurant.id")
    OrderEntity toOrderEntity(Order order);
    @Mapping(target = "restaurant.id", source = "restaurantEntity.id")
    Order toOrder(OrderEntity orderEntity);
    @Mapping(target = "restaurant.id", source = "restaurantEntity.id")
    List<Order> toOrderList(List<OrderEntity> orderEntityList);
}
