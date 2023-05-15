package com.pragma.powerup.usermicroservice.adapters.driving.http.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.UserNotFoundException;
import com.pragma.powerup.usermicroservice.adapters.driving.http.exceptions.UserRoleNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class OwnerHttpAdapter {
    private final RestTemplate restTemplate;

    @Autowired
    public OwnerHttpAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${app.urls.urlToOwner}")
    String urlToOwner;

    @GetMapping("/{id}")
    public void getOwner(@PathVariable Integer id) {
        try {
            String url = urlToOwner + "owner/getOwner/" + id;
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, String> responseData = response.getBody();
                if (responseData == null) {
                    throw new UserNotFoundException();
                }
            } else {
                throw new UserRoleNotFoundException();
            }
        } catch (Exception ex) {
            throw new UserRoleNotFoundException();
        }
    }
}
