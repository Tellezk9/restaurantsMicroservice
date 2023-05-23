package com.pragma.powerup.usermicroservice.adapters.driving.http.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found");
    }
}
