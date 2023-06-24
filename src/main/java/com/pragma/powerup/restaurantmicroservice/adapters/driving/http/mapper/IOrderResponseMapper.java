package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.OrderDocumentResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.OrderDurationResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.RankingEmployeeResponseDto;
import com.pragma.powerup.restaurantmicroservice.domain.model.Order;
import com.pragma.powerup.restaurantmicroservice.domain.model.OrderDocument;
import com.pragma.powerup.restaurantmicroservice.domain.model.RankingEmployee;
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
    List<OrderDurationResponseDto> toListOrderDurationResponseDto(List<OrderDocument> order);
    @Mapping(target = "averageMinutes",source = "average")
    List<RankingEmployeeResponseDto> toListRankingEmployeeResponseDto(List<RankingEmployee> rankingEmployee);
    @Mapping(target = "averageMinutes",source = "average")
    RankingEmployeeResponseDto toRankingEmployeeResponseDto(RankingEmployee rankingEmployee);
}
