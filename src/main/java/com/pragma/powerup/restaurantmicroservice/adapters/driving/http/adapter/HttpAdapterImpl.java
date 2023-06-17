package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.adapter;

import com.pragma.powerup.restaurantmicroservice.adapters.driven.jpa.mysql.exceptions.UserNotFoundException;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.dto.request.ClientResponseDto;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.exceptions.ErrorExecutionException;
import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.exceptions.UserRoleNotFoundException;

import com.pragma.powerup.restaurantmicroservice.adapters.driving.http.handlers.IClientHandler;
import com.pragma.powerup.restaurantmicroservice.domain.geteway.IHttpAdapter;
import com.pragma.powerup.restaurantmicroservice.domain.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class HttpAdapterImpl implements IHttpAdapter {
    private final RestTemplate restTemplate;
    private final IClientHandler clientHandler;

    @Autowired
    public HttpAdapterImpl(RestTemplate restTemplate, IClientHandler clientHandler) {
        this.restTemplate = restTemplate;
        this.clientHandler = clientHandler;
    }

    @Value("${app.urls.urlToUserMicroService}")
    String urlToUserMicroService;
    @Value("${app.urls.urlToMessagingMicroService}")
    String urlToMessagingMicroService;

    public void getOwner(Long id, String token) {
        try {
            String bearerToken = "Bearer " + token;
            Integer idOwner = Integer.valueOf(Long.toString(id));
            String url = urlToUserMicroService + "owner/getOwner/{id}";

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", bearerToken);

            HttpEntity entity = new HttpEntity<>(headers);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class, idOwner);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new UserRoleNotFoundException();
            }
            if (response.getBody() == null) {
                throw new UserNotFoundException();
            }
        } catch (Exception ex) {
            throw new ErrorExecutionException();
        }
    }

    public Client getClient(Long id, String token) {
        ResponseEntity<ClientResponseDto> response;
        String bearerToken = "Bearer " + token;
        Integer idClient = Integer.valueOf(Long.toString(id));
        String url = urlToUserMicroService + "client/getClient/{id}";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", bearerToken);

        HttpEntity entity = new HttpEntity<>(headers);
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, entity, ClientResponseDto.class, idClient);
        } catch (Exception ex) {
            throw new ErrorExecutionException();
        }
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new UserRoleNotFoundException();
        }
        if (response.getBody() == null) {
            throw new UserNotFoundException();
        }
        return clientHandler.toClient(response.getBody());
    }

    public void sendNotification(Client client, String token) {
        ResponseEntity response;
        String bearerToken = "Bearer " + token;
        String url = urlToMessagingMicroService + "sms";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", bearerToken);

        HttpEntity entity = new HttpEntity<>(client,headers);
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
        } catch (Exception ex) {
            throw new ErrorExecutionException();
        }
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new UserRoleNotFoundException();
        }
        if (response.getBody() == null) {
            throw new UserNotFoundException();
        }
    }
}
