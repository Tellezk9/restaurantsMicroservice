package com.pragma.powerup.usermicroservice.domain.exceptions;

public class InvalidRestaurantNameException extends RuntimeException{
    public InvalidRestaurantNameException(){
        super("Invalid restaurant name");
    }
}
