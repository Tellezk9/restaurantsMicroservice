package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found");
    }
}
