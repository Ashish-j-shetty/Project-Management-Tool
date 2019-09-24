package com.fullstack.pmtool.web;


import com.fullstack.pmtool.domain.User;
import com.fullstack.pmtool.services.MapValidationErrorService;
import com.fullstack.pmtool.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user , BindingResult bindingResult){
        //validate passwords match

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(bindingResult);

        if(errorMap !=null)
            return  errorMap;

        User newUser=userService.saveUser(user);

        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);

    }




}
