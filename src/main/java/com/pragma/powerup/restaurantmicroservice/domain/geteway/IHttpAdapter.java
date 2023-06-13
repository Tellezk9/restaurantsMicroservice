package com.pragma.powerup.restaurantmicroservice.domain.geteway;

public interface IHttpAdapter {
    void getOwner(Long id, String token);
}
