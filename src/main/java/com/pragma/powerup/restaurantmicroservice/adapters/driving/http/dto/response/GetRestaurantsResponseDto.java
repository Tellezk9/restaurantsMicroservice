package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetRestaurantsResponseDto {
    private Long id;
    private String urlLogo;
    private String name;
}
