package com.example.OnlineFoodOrderingSystem.controller;

import com.example.OnlineFoodOrderingSystem.entities.User;
import com.example.OnlineFoodOrderingSystem.model.AuthResponse;
import com.example.OnlineFoodOrderingSystem.repository.CartRepository;
import com.example.OnlineFoodOrderingSystem.repository.UserRepository;
import com.example.OnlineFoodOrderingSystem.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private CartRepository cartRepository;

    public ResponseEntity<AuthResponse> createUser(@RequestBody User user){
        if (userRepo.findById(user.getUserId()).isPresent()) {
            throw new RuntimeException("Usre already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepo.save(user);
        return new ResponseEntity<>()
    }

}
