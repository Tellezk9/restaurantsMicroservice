package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper.IEmployeeRequestMapper;
import com.pragma.powerup.restaurantmicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.restaurantmicroservice.domain.model.Employee;
import com.pragma.powerup.restaurantmicroservice.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
@ExtendWith(MockitoExtension.class)
class EmployeeHandlerImplTest {
    @Mock
    private IRestaurantServicePort restaurantServicePort;
    @Mock
    private IEmployeeRequestMapper employeeRequestMapper;
    @InjectMocks
    private EmployeeHandlerImpl employeeHandler;

    @Test
    void saveEmployee() {
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto(1,1);
        Restaurant restaurant = new Restaurant(1L, "name", "address", 1L, "123", "urlLogo", 123);
        Employee employee = new Employee(1L,1L, restaurant);

        doNothing().when(restaurantServicePort).saveRestaurantEmployee(employee);
        when(employeeRequestMapper.toEmployee(employeeRequestDto)).thenReturn(employee);

        employeeHandler.saveEmployee(employeeRequestDto);

        verify(restaurantServicePort, times(1)).saveRestaurantEmployee(employee);
        verify(employeeRequestMapper, times(1)).toEmployee(employeeRequestDto);
    }
}