package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class OrderRequestDto {
    @Min(1)
    private Long idRestaurant;
    @NotNull
    @Size(min = 1, message = "The order cannot be empty")
    private List<Long> orderDishes;
    @Size(min = 1, message = "The amount of dishes cannot be empty")
    private List<Integer> amountDishes;
}
