package com.pragma.powerup.usermicroservice.adapters.driving.http.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.UserNotFoundException;
import com.pragma.powerup.usermicroservice.adapters.driving.http.exceptions.ErrorExecutionException;
import com.pragma.powerup.usermicroservice.adapters.driving.http.exceptions.UserRoleNotFoundException;

import com.pragma.powerup.usermicroservice.domain.geteway.IHttpAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class HttpAdapterImpl implements IHttpAdapter {
    private final RestTemplate restTemplate;

    @Autowired
    public HttpAdapterImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${app.urls.urlToUserMicroService}")
    String urlToOwner;

    @GetMapping("/{id}")
    public void getOwner(@PathVariable Long id, String token) {
        try {
            String bearerToken = "Bearer " + token;
            Integer idOwner = Integer.valueOf(Long.toString(id));
            String url = urlToOwner + "owner/getOwner/{id}";

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
}
