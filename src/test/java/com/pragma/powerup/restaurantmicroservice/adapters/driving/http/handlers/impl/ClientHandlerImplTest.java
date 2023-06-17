package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.ClientResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.mapper.IClientResponseMapper;
import com.pragma.powerup.restaurantmicroservice.domain.model.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientHandlerImplTest {

    @Mock
    private IClientResponseMapper clientResponseMapper;
    @InjectMocks
    private ClientHandlerImpl clientHandler;

    @Test
    void toClient() {
        ClientResponseDto clientResponseDto = new ClientResponseDto(1, "", "", 1, "", "", "");
        Client client = new Client(1L, null, "", "", 1, "", "", "");

        when(clientResponseMapper.toClient(clientResponseDto)).thenReturn(client);

        clientHandler.toClient(clientResponseDto);

        verify(clientResponseMapper,times(1)).toClient(clientResponseDto);

    }
}