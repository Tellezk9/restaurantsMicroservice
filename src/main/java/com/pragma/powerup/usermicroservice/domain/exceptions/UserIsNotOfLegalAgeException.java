package com.pragma.powerup.usermicroservice.domain.exceptions;

public class UserIsNotOfLegalAgeException extends RuntimeException{
    public UserIsNotOfLegalAgeException(){super("User is not of legal age");}
}
