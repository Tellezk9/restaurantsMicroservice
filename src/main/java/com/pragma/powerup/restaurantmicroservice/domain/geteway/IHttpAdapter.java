package com.pragma.powerup.restaurantmicroservice.domain.geteway;

import com.pragma.powerup.restaurantmicroservice.domain.model.Client;

public interface IHttpAdapter {
    void getOwner(Long id, String token);
    Client getClient(Long id, String token);
    void sendNotification(Client client, String token);
}
