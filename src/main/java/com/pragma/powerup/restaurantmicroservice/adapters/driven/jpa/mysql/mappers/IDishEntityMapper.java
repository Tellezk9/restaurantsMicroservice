package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.restaurantmicroservice.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishEntityMapper {
    @Mapping(target = "restaurantEntity.id",source = "restaurant.id")
    @Mapping(target = "categoryEntity.id",source = "category.id")
    DishEntity toDishEntity(Dish dish);

    @Mapping(target = "restaurant.id",source = "restaurantEntity.id")
    @Mapping(target = "category.id",source = "categoryEntity.id")
    Dish toDish(DishEntity dishEntity);

    @Mapping(target = "restaurant.id",source = "restaurantEntity.id")
    @Mapping(target = "category.id",source = "categoryEntity.id")
    List<Dish> toListDish(List<DishEntity> dishEntities);
}
