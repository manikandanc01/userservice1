package com.example.userservice.controllers;


import com.example.userservice.dtos.*;
import com.example.userservice.models.SessionStatus;
import com.example.userservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService)
    {
        this.authService=authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request) throws Exception
    {
          return authService.login(request.getEmail(),request.getPassword());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto request)
    {
         authService.logout(request.getToken(), request.getUserId());

         return ResponseEntity.ok().build();

    }

    //Creating new User
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignUpRequestDto request) throws Exception
    {
      UserDto userDto=authService.signUp(request.getEmail(), request.getPassword());
      return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validate(@RequestBody ValidateTokenRequestDto request)
    {
        SessionStatus sessionStatus=authService.validate(request.getToken(), request.getUserId());
        return new ResponseEntity<>(sessionStatus,HttpStatus.OK);
    }



}
