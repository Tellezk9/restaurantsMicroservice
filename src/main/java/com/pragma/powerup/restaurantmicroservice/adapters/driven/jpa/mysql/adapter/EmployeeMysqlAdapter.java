package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.EmployeeEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.EmployeeAlreadyAssignedException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.EmployeeDoesNotBelongRestaurantException;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers.IEmployeeEntityMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories.IEmployeeRepository;
import com.pragma.powerup.restaurantmicroservice.domain.model.Employee;
import com.pragma.powerup.restaurantmicroservice.domain.spi.IEmployeePersistencePort;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class EmployeeMysqlAdapter implements IEmployeePersistencePort {
    private final IEmployeeRepository employeeRepository;
    private final IEmployeeEntityMapper employeeEntityMapper;

    @Override
    public void saveEmployee(Employee employee) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findByIdEmployee(employee.getIdEmployee());

        if (employeeEntity.isPresent()){
            throw new EmployeeAlreadyAssignedException();
        }

        employeeRepository.save(employeeEntityMapper.toEmployeeEntity(employee));
    }

    @Override
    public Employee getEmployeeByIdEmployeeAndIdRestaurant(Long idEmployee, Long idRestaurant) {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findByIdEmployeeAndRestaurantEntityId(idEmployee,idRestaurant);

        if (!employeeEntity.isPresent()){
            throw new EmployeeDoesNotBelongRestaurantException();
        }
        return employeeEntityMapper.toEmployee(employeeEntity.get());
    }
}
