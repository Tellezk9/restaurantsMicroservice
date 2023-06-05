package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;

public interface IEmployeeHandler {
    void saveEmployee(EmployeeRequestDto employeeRequestDto);
}
