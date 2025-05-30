package com.example.OnlineFoodOrderingSystem.controller;

import com.example.OnlineFoodOrderingSystem.entities.User;
import com.example.OnlineFoodOrderingSystem.model.AuthResponse;
import com.example.OnlineFoodOrderingSystem.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    public ResponseEntity<AuthResponse> createUser(@Valid @RequestBody  User user){
        AuthResponse authResponse = authService.createUser(user);
         return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }


    public ResponseEntity<User> login(@RequestBody User user){
        User savedUser = authService.login(user);

         return new ResponseEntity<>();
    }
}