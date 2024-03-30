package com.example.userservice.Exceptions;

public class PasswordNotMatchException extends Exception{

    public PasswordNotMatchException(String message) {
        super(message);
    }
}
