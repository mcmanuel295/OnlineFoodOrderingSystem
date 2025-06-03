package com.example.OnlineFoodOrderingSystem.controller;

import com.example.OnlineFoodOrderingSystem.entities.User;
import com.example.OnlineFoodOrderingSystem.model.AuthResponse;
import com.example.OnlineFoodOrderingSystem.model.LoginRequest;
import com.example.OnlineFoodOrderingSystem.service.AuthService;
import com.example.OnlineFoodOrderingSystem.service.intf.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUser(@Valid @RequestBody User user){
        System.out.println(user.getFullName()+ " "+user.getEmail()+" "+" " +user.getPassword()+" "+user.getRole());
        AuthResponse authResponse = authService.createUser(user);
         return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }


    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest){
         return new ResponseEntity<>(authService.login(loginRequest),HttpStatus.OK);
    }
}