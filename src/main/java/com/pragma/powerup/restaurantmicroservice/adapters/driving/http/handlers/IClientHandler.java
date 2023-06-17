package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.ClientResponseDto;
import com.pragma.powerup.restaurantmicroservice.domain.model.Client;

public interface IClientHandler {
    Client toClient(ClientResponseDto clientResponseDto);
}
