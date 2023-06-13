package com.pragma.powerup.restaurantmicroservice.domain.exceptions;

public class InvalidRestaurantNameException extends RuntimeException{
    public InvalidRestaurantNameException(){
        super("Invalid restaurant name");
    }
}
