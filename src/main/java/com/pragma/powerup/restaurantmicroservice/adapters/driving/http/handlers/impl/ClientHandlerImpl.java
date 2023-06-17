package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.ClientResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.IClientHandler;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper.IClientResponseMapper;
import com.pragma.powerup.restaurantmicroservice.domain.model.Client;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ClientHandlerImpl implements IClientHandler {
    private final IClientResponseMapper clientResponseMapper;

    @Override
    public Client toClient(ClientResponseDto clientResponseDto) {
        return clientResponseMapper.toClient(clientResponseDto);
    }

}
