package com.pragma.powerup.restaurantmicroservice.domain.exceptions;

public class InvalidFormatDateException extends RuntimeException{
    public InvalidFormatDateException(){super("Invalid format date");}
}
