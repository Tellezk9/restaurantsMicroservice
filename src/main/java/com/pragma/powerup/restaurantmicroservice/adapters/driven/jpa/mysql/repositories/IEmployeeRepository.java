package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IEmployeeRepository extends JpaRepository<EmployeeEntity,Long> {
    Optional<EmployeeEntity> findByIdEmployee(Long idEmployee);
}
