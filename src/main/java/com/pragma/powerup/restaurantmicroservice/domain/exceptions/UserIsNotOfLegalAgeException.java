package com.pragma.powerup.restaurantmicroservice.domain.exceptions;

public class UserIsNotOfLegalAgeException extends RuntimeException{
    public UserIsNotOfLegalAgeException(){super("User is not of legal age");}
}
