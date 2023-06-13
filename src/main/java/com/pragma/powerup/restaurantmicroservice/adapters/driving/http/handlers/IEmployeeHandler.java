package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;

public interface IEmployeeHandler {
    void saveEmployee(EmployeeRequestDto employeeRequestDto);
}
