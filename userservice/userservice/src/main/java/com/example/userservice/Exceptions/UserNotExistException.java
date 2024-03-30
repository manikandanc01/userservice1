package com.example.userservice.Exceptions;

public class UserNotExistException extends Exception{
    public UserNotExistException(String message) {
        super(message);
    }
}
