package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class OrderDurationResponseDto {
    private Long idOrder;
    private Date dateInit;
    private Date dateEnd;
    private List<Map<String, String>> order;
}
