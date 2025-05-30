package com.example.OnlineFoodOrderingSystem.service;

import com.example.OnlineFoodOrderingSystem.entities.Cart;
import com.example.OnlineFoodOrderingSystem.entities.User;
import com.example.OnlineFoodOrderingSystem.model.AuthResponse;
import com.example.OnlineFoodOrderingSystem.model.LoginRequest;
import com.example.OnlineFoodOrderingSystem.repository.CartRepository;
import com.example.OnlineFoodOrderingSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private CartRepository cartRepository;


    public AuthResponse createUser(User user) {
        if (userRepo.findById(user.getUserId()).isPresent()) {
            throw new RuntimeException("User already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Cart cart = new Cart();
        cart.setCustomer(user);
        cartRepository.save(cart);

        return AuthResponse
                .builder()
                .user(userRepo.save(user))
                .message("User created successfully")
                .build();
    }


    public User login(LoginRequest loginRequest) {

    }
}
