package com.fullstack.pmtool.services;

import com.fullstack.pmtool.domain.User;
import com.fullstack.pmtool.exceptions.UsernameAlreadyExistsException;
import com.fullstack.pmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; //to encode password

    public User saveUser(User newUser){

       try{
           newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
           //username has to be unique (requires custom exception);

           newUser.setUsername(newUser.getUsername());
           //Make sure that password and confirm password match
           //Dont persist  or show the confirm password.
           newUser.setConfirmPassword("");
           return  userRepository.save(newUser);

       }catch(Exception e){
            throw  new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"'alredy exitsts");
       }

    }
}
