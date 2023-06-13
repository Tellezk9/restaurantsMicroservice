package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishResponseMapper {
    @Mapping(source = "category.id", target = "idCategory")
    List<DishResponseDto> toListDishResponseDto(List<Dish> dish);
    @Mapping(source = "category.id", target = "idCategory")
    DishResponseDto toDishResponseDto(Dish dish);
}
