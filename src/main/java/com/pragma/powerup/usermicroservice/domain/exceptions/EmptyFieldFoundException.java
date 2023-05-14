package com.pragma.powerup.usermicroservice.domain.exceptions;

public class EmptyFieldFoundException extends RuntimeException{
    public EmptyFieldFoundException(){super("empty field found");}
}
