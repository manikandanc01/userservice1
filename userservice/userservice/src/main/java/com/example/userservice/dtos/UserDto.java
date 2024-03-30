package com.example.userservice.dtos;


import com.example.userservice.models.Role;
import com.example.userservice.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter

public class UserDto {
    private String email;
    private Set<Role> roles=new HashSet<>();

    //Create a deep copy from existing User object
    public static UserDto from(User user)
    {
        UserDto userDto=new UserDto();
        userDto.setEmail(user.getEmail());
        return userDto;
    }

}
