package com.example.userservice.service;


import com.example.userservice.Exceptions.PasswordNotMatchException;
import com.example.userservice.Exceptions.UserAlreadyExistException;
import com.example.userservice.Exceptions.UserNotExistException;
import com.example.userservice.dtos.UserDto;
import com.example.userservice.models.Session;
import com.example.userservice.models.SessionStatus;
import com.example.userservice.models.User;
import com.example.userservice.repository.SessionRepository;
import com.example.userservice.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthService {

    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UserRepository userRepository,SessionRepository sessionRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository=sessionRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    public ResponseEntity<UserDto> login(String email, String password) throws Exception
    {
       Optional<User> userOptional= userRepository.findByEmail(email);
       if(userOptional.isEmpty())
           throw new UserNotExistException("The user does not exists. Please signup if you are a new user");

        User user=userOptional.get();

        if(!bCryptPasswordEncoder.matches(password, user.getPassword()))
            throw new PasswordNotMatchException("Invalid Password");

        String token= RandomStringUtils.randomAlphanumeric(30);

        Session session=new Session();
        session.setToken(token);
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setUser(user);

        sessionRepository.save(session);

        UserDto userDto=UserDto.from(user);

        MultiValueMap<String,String> headers=new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE,"auth-token"+token);


        return new ResponseEntity<>(userDto,headers, HttpStatus.OK);
    }

    public void logout(String token,Long userId)
    {
      Optional<Session> sessionOptional=sessionRepository.findByTokenAndUser_Id(token,userId);

      if(sessionOptional.isEmpty())
          return;

      Session session= sessionOptional.get();

      session.setSessionStatus(SessionStatus.ENDED);

      sessionRepository.save(session);

    }
    public UserDto signUp(String email,String password) throws Exception
    {
        Optional<User> userOptional= userRepository.findByEmail(email);
        if(userOptional.isPresent())
            throw new UserAlreadyExistException("The user is already exists. Please login to continue");

        User user=new User();
        user.setEmail(email);

        user.setPassword(bCryptPasswordEncoder.encode(password));

        User savedUser=userRepository.save(user);

        return UserDto.from(savedUser);
    }

    public SessionStatus validate(String token,Long userId)
    {
      Optional<Session> sessionOptional=sessionRepository.findByTokenAndUser_Id(token,userId);

      if(sessionOptional.isEmpty())
          return SessionStatus.ENDED;

      Session session= sessionOptional.get();

      if(!session.getSessionStatus().equals(SessionStatus.ACTIVE))
      {
          return SessionStatus.ENDED;
      }

      return SessionStatus.ACTIVE;
    }
}
