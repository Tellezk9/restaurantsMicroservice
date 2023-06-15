package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class OrderResponseDto {
    private Long id;
    private Long idClient;
    private Date date;
    private Long status;
    private Long idChef;
    private Long idRestaurant;
}
