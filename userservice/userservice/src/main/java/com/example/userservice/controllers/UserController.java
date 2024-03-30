package com.example.userservice.controllers;


import com.example.userservice.dtos.SetUserRoleRequestDto;
import com.example.userservice.dtos.UserDto;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")


public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService  userService)
    {
        this.userService=userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetails(@PathVariable("id") Long userId)
    {
        UserDto userDto=userService.getUserDetails(userId);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/{id}/roles")
    public ResponseEntity<UserDto> setUserRoles(@PathVariable("id") Long userId, @RequestBody SetUserRoleRequestDto request)
    {
        UserDto userDto=userService.setUserRoles(userId,request.getRoleIDs());

        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }
}
