package com.example.userservice.Exceptions;

public class JwtVerificationException extends Exception{
    public JwtVerificationException(String message) {
        super(message);
    }
}
