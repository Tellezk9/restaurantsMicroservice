package com.pragma.powerup.usermicroservice.domain.geteway;

public interface IHttpAdapter {
    void getOwner(Long id, String token);
}
