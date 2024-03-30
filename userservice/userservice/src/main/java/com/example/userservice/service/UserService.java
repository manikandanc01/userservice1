package com.example.userservice.service;


import com.example.userservice.dtos.UserDto;
import com.example.userservice.models.Role;
import com.example.userservice.models.User;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private RoleRepository roleRepository;
    private UserRepository userRepository;

    public UserService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public UserDto getUserDetails(Long userId)
    {
        return new UserDto();
    }

    public UserDto setUserRoles(Long userId, List<Long> roleIds)
    {
      Optional<User> userOptional=userRepository.findById(userId);
      List<Role> roles=roleRepository.findAllById(roleIds);

      if(userOptional.isEmpty())
        return null;

      User user=userOptional.get();
      user.setRoles(Set.copyOf(roles));

      User savedUser=userRepository.save(user);

      return UserDto.from(savedUser);

    }
}
