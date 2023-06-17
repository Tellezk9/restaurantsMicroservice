package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.ClientResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.IClientHandler;
import com.pragma.powerup.restaurantmicroservice.domain.model.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HttpAdapterImplTest {
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private IClientHandler clientHandler;
    @InjectMocks
    private HttpAdapterImpl httpAdapterImpl;

    @Test
    void getOwnerSuccess() {
        String token = "TestToken";
        String url = null + "owner/getOwner/{id}";
        Map<String, String> data = new HashMap<>();
        data.put("testId", "testBody");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = new ResponseEntity<>(data, HttpStatus.OK);

        when(restTemplate.exchange(url, HttpMethod.GET, entity, Map.class, 1)).thenReturn(response);

        httpAdapterImpl.getOwner(1L, token);

        verify(restTemplate, times(1)).exchange(url, HttpMethod.GET, entity, Map.class, 1);

    }

    @Test
    void getClient() {
        Long id = 1L;
        String token = "TestToken";
        String url = null + "client/getClient/{id}";
        ClientResponseDto clientResponseDto = new ClientResponseDto(1, "", "", 1, "", "", "");
        Client client = new Client(1L, null, "", "", 1, "", "", "");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity<ClientResponseDto> response = new ResponseEntity<>(
                clientResponseDto,
                HttpStatusCode.valueOf(200)
        );

        when(restTemplate.exchange(url, HttpMethod.GET, entity, ClientResponseDto.class, Integer.valueOf(String.valueOf(id)))).thenReturn(response);
        when(clientHandler.toClient(clientResponseDto)).thenReturn(client);
        httpAdapterImpl.getClient(id, token);

        verify(restTemplate, times(1)).exchange(url, HttpMethod.GET, entity, ClientResponseDto.class, Integer.valueOf(String.valueOf(id)));

    }

    @Test
    void sendNotification() {
        String token = "TestToken";
        String url = null + "sms";
        Client client = new Client(1L, null, "", "", 1, "", "", "");

        Map<String, String> data = new HashMap<>();
        data.put("testId", "testBody");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity entity = new HttpEntity<>(client, headers);
        ResponseEntity response = new ResponseEntity<>(data, HttpStatus.OK);

        when(restTemplate.exchange(url, HttpMethod.POST, entity, Map.class)).thenReturn(response);

        httpAdapterImpl.sendNotification(client, token);

        verify(restTemplate, times(1)).exchange(url, HttpMethod.POST, entity, Map.class);

    }
}