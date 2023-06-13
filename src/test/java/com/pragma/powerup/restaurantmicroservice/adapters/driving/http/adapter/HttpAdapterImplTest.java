package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.adapter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HttpAdapterImplTest {
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private HttpAdapterImpl httpAdapterImpl;

    @Value("${app.urls.urlToOwner}")
    String urlToOwner;

    @Test
    void getOwnerSuccess() {
        String token = "TestToken";
        String url = urlToOwner + "owner/getOwner/{id}";
        Map<String, String> data = new HashMap<>();
        data.put("testId", "testBody");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " +token);
        HttpEntity entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = new ResponseEntity<>(data, HttpStatus.OK);

        when(restTemplate.exchange(url,HttpMethod.GET,entity, Map.class, 1)).thenReturn(response);

        httpAdapterImpl.getOwner(1L, token);

        verify(restTemplate, times(1)).exchange(url,HttpMethod.GET,entity, Map.class, 1);

    }
}