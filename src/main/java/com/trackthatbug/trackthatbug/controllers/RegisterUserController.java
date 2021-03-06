package com.trackthatbug.trackthatbug.controllers;

import com.trackthatbug.trackthatbug.models.RegisterUserResponse;
import com.trackthatbug.trackthatbug.models.Result;
import com.trackthatbug.trackthatbug.models.User;
import com.trackthatbug.trackthatbug.repositories.UserRepository;
import com.trackthatbug.trackthatbug.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RegisterUserController {
    private UserDetailsServiceImpl userDetailsService;
    private UserRepository userRepository;

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/registerUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Result<RegisterUserResponse>> registerUser(@RequestBody @Valid User user, BindingResult result){
        Result<RegisterUserResponse> registerUserResult = new Result<>();
        registerUserResult.setPayload(new RegisterUserResponse());
        if(result.hasErrors()){
            registerUserResult.setError(true);
            registerUserResult.getPayload().setConfirmationMessage("Validation error occurred");
            return new ResponseEntity<>(registerUserResult, HttpStatus.OK);
        }

        //register new user then return to login if successful`
        User available = userRepository.findByUsername(user.getUsername());
        if(available == null){
            //add user
            try {
                userDetailsService.saveUser(user);
                registerUserResult.getPayload().setConfirmationMessage("New user " + user.getUsername() + " created.");
            } catch(Exception e){
                registerUserResult.setError(true);
            }
        } else {
            //show error
            registerUserResult.setError(true);
            registerUserResult.getPayload().setConfirmationMessage("Username unavailable");
        }

        return new ResponseEntity<>(registerUserResult, HttpStatus.OK);
    }

    public UserDetailsServiceImpl getUserDetailsService() {
        return userDetailsService;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
