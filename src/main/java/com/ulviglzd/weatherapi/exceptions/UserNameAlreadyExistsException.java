package com.ulviglzd.weatherapi.exceptions;

public class UserNameAlreadyExistsException extends RuntimeException{
    public UserNameAlreadyExistsException(String message) {
        super(message);
    }
}
