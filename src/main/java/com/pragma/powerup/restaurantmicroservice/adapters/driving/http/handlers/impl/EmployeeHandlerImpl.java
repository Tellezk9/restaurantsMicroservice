package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.IEmployeeHandler;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper.IEmployeeRequestMapper;
import com.pragma.powerup.restaurantmicroservice.domain.api.IRestaurantServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeHandlerImpl implements IEmployeeHandler {
    private final IRestaurantServicePort restaurantServicePort;
    private final IEmployeeRequestMapper employeeRequestMapper;

    @Override
    public void saveEmployee(EmployeeRequestDto employeeRequestDto) {
        restaurantServicePort.saveRestaurantEmployee(employeeRequestMapper.toEmployee(employeeRequestDto));
    }
}
