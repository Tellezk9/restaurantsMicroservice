package com.pragma.powerup.restaurantmicroservice.adapters.driving.http.exceptions;

public class UserRoleNotFoundException extends RuntimeException {
    public UserRoleNotFoundException() {
        super("User role not found");
    }
}
