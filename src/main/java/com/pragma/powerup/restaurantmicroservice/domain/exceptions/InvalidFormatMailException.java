package com.pragma.powerup.restaurantmicroservice.domain.exceptions;

public class InvalidFormatMailException extends RuntimeException{
    public InvalidFormatMailException(){super("Invalid format mail");}
}
