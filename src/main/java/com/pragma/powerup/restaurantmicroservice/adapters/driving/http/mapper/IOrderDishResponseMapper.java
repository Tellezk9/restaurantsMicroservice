package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.OrderDishResponseDto;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderDishResponseMapper {
    @Mapping(target = "nameDish",source = "orderDish.name")
    @Mapping(target = "amount",source = "amount")
    List<OrderDishResponseDto> toListOrderDishResponseDto(List<OrderDish> orderDishes);
    @Mapping(target = "nameDish",source = "orderDish.name")
    @Mapping(target = "amount",source = "amount")
    OrderDishResponseDto toOrderDishResponseDto(OrderDish orderDishes);
}
