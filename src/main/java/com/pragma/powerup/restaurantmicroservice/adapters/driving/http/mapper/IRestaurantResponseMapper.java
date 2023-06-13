package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.GetRestaurantsResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantResponseMapper {
    List<RestaurantResponseDto> toListRestaurantResponseDto(List<Restaurant> restaurant);
    RestaurantResponseDto toRestaurantResponseDto(Restaurant restaurant);
    List<GetRestaurantsResponseDto> toGetRestaurantResponse(List<Restaurant> restaurants);
}
