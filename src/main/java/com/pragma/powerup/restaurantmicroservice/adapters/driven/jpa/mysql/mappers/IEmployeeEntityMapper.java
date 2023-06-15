package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.EmployeeEntity;
import com.pragma.powerup.restaurantmicroservice.domain.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IEmployeeEntityMapper {
    @Mapping(target = "restaurantEntity.id",source = "idRestaurant.id")
    EmployeeEntity toEmployeeEntity(Employee employee);
    @Mapping(target = "idRestaurant.id",source = "restaurantEntity.id")
    Employee toEmployee(EmployeeEntity employeeEntity);
}
