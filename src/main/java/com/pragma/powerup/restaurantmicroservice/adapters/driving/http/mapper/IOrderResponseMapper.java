package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.OrderDocumentResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderResponseMapper {
    @Mapping(target = "idRestaurant", source = "restaurant.id")
    List<OrderResponseDto> toListOrderResponseDto(List<Order> orders);
    @Mapping(target = "idRestaurant", source = "restaurant.id")
    OrderResponseDto toOrderResponseDto(Order order);
    OrderDocumentResponseDto toOrderDocumentResponseDto(OrderDocument orderDocument);
    List<OrderDocumentResponseDto> toOrderDocumentResponseDtoList(List<OrderDocument> orderDocument);
}
