package com.pragma.powerup.usermicroservice.adapters.driving.http.adapter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerHttpAdapterTest {
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private OwnerHttpAdapter ownerHttpAdapter;

    @Value("${app.urls.urlToOwner}")
    String urlToOwner;

    @Test
    void getOwnerSuccess() {
        String url = urlToOwner + "owner/getOwner/" + 1;
        Map<String, String> data = new HashMap<>();
        data.put("testId", "testBody");
        ResponseEntity<Map> response = new ResponseEntity<>(data, HttpStatus.OK);

        when(restTemplate.getForEntity(url, Map.class)).thenReturn(response);

        ownerHttpAdapter.getOwner(1);

        verify(restTemplate, times(1)).getForEntity(url, Map.class);

    }
}