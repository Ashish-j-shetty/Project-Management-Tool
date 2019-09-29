package com.fullstack.pmtool.web;


import com.fullstack.pmtool.domain.User;
import com.fullstack.pmtool.payload.JWTLoginSuccessResponse;
import com.fullstack.pmtool.payload.LoginRequest;
import com.fullstack.pmtool.security.JwtTokenProvider;
import com.fullstack.pmtool.services.MapValidationErrorService;
import com.fullstack.pmtool.services.UserService;
import com.fullstack.pmtool.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.fullstack.pmtool.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public  ResponseEntity<?>authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
            ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
            if(errorMap !=null) return errorMap;

        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt=TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
        return  ResponseEntity.ok(new JWTLoginSuccessResponse( true,jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user , BindingResult bindingResult){


        //validate passwords match

        userValidator.validate(user,bindingResult);

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(bindingResult);

        if(errorMap !=null)
            return  errorMap;

        User newUser=userService.saveUser(user);

        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);

    }




}
