package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RankingEmployeeResponseDto {
    private Long idEmployee;
    private Long averageMinutes;
}
