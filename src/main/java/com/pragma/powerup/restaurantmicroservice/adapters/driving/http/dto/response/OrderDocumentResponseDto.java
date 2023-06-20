package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class OrderDocumentResponseDto {
    private Long idOrder;
    private Long idClient;
    private Long idEmployee;
    private Date dateInit;
    private Date dateEnd;
    private Long previousStatus;
    private Long actualStatus;
    private List<Map<String, String>> order;
}