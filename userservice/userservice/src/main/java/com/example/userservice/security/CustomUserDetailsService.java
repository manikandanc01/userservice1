package com.example.userservice.security;

import com.example.userservice.models.User;
import com.example.userservice.repository.UserRepository;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Getter
@Setter
@JsonDeserialize(as = CustomUserDetailsService.class)
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optionalUser=userRepository.findByEmail(username);
        if(optionalUser.isEmpty())
            throw new UsernameNotFoundException("The Username is not found");

        User user= optionalUser.get();

        return new CustomUserDetail(user);
    }
}
