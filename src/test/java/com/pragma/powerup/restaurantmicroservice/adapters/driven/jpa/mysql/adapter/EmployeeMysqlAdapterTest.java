package com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.EmployeeEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.mappers.IEmployeeEntityMapper;
import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.repositories.IEmployeeRepository;
import com.pragma.powerup.restaurantmicroservice.domain.model.Employee;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeMysqlAdapterTest {
    @Mock
    private IEmployeeRepository employeeRepository;
    @Mock
    private IEmployeeEntityMapper employeeEntityMapper;
    @InjectMocks
    private EmployeeMysqlAdapter employeeMysqlAdapter;

    @Test
    void saveEmployee() {
        Restaurant restaurant = new Restaurant(1L, "testName", "string", 4L, "+439094230412", "string", 123);
        Employee employee = new Employee(null,1L,restaurant);
        RestaurantEntity restaurantEntity = new RestaurantEntity(1L, "testName", "string", 4L, "+439094230412", "string", "1233");
        EmployeeEntity employeeEntity = new EmployeeEntity(1L,1L,restaurantEntity);

        when(employeeRepository.findByIdEmployee(employee.getIdEmployee())).thenReturn(Optional.empty());
        when(employeeEntityMapper.toEmployeeEntity(employee)).thenReturn(employeeEntity);

        employeeMysqlAdapter.saveEmployee(employee);

        verify(employeeRepository,times(1)).findByIdEmployee(employee.getIdEmployee());
        verify(employeeEntityMapper,times(1)).toEmployeeEntity(employee);
    }

    @Test
    void changeDishState() {
        Restaurant restaurant = new Restaurant(1L, "testName", "string", 4L, "+439094230412", "string", 123);
        Employee employee = new Employee(null,1L,restaurant);
        RestaurantEntity restaurantEntity = new RestaurantEntity(1L, "testName", "string", 4L, "+439094230412", "string", "1233");
        EmployeeEntity employeeEntity = new EmployeeEntity(1L,1L,restaurantEntity);

        when(employeeRepository.findByIdEmployee(employee.getIdEmployee())).thenReturn(Optional.empty());
        when(employeeEntityMapper.toEmployeeEntity(employee)).thenReturn(employeeEntity);

        employeeMysqlAdapter.saveEmployee(employee);

        verify(employeeRepository,times(1)).findByIdEmployee(employee.getIdEmployee());
        verify(employeeEntityMapper,times(1)).toEmployeeEntity(employee);
    }
}