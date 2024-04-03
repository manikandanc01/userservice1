package com.example.userservice.Exceptions;

import com.example.userservice.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ExceptionDto> handleUserAlreadyExistException(UserAlreadyExistException userAlreadyExistException)
    {
            return new ResponseEntity<>(new ExceptionDto(HttpStatus.CONFLICT, userAlreadyExistException.getMessage())
                    ,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<ExceptionDto> handleNotExistException(UserNotExistException userNotExistException)
    {
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.NOT_FOUND, userNotExistException.getMessage())
                ,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<ExceptionDto> handlePasswordMisMatchException(PasswordNotMatchException passwordNotMatchException)
    {
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.UNAUTHORIZED, passwordNotMatchException.getMessage())
                ,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtVerificationException.class)
    public ResponseEntity<ExceptionDto> handleJwtVerificationException(JwtVerificationException jwtVerificationException)
    {
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.UNAUTHORIZED,jwtVerificationException.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }


}
