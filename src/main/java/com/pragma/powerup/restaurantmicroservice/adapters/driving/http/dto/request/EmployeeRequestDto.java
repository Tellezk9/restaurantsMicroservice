package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class EmployeeRequestDto {
    @Min(1)
    private Integer idEmployee;
    @Min(1)
    private Integer idRestaurant;
}
