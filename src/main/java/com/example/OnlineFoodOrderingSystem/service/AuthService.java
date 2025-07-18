package com.example.OnlineFoodOrderingSystem.service;

import com.example.OnlineFoodOrderingSystem.entities.Cart;
import com.example.OnlineFoodOrderingSystem.entities.User;
import com.example.OnlineFoodOrderingSystem.pojo.AuthResponse;
import com.example.OnlineFoodOrderingSystem.request.LoginRequest;
import com.example.OnlineFoodOrderingSystem.pojo.USER_ROLE;
import com.example.OnlineFoodOrderingSystem.repository.CartRepository;
import com.example.OnlineFoodOrderingSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final CartRepository cartRepository;


    public AuthResponse   createUser(User user) {
        if (user.getUserId() != null && userRepo.findById(user.getUserId()).isPresent()) {
            throw new RuntimeException("User already exist or invalid user Id");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null) {
            user.setRole(USER_ROLE.ROLE_USER);
        }
        User savedUser = userRepo.save(user);

        Cart cart = new Cart();
        cart.setCustomer(savedUser);
        cartRepository.save(cart);

        return AuthResponse
                .builder()
                .user(savedUser)
                .message("User created successfully")
                .build();
    }


    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword());
        Authentication authentication = authManager.authenticate(authToken);

        String token = jwtService.generateToken(authentication);
        return AuthResponse.builder()
                .jwt(token)
                .message("Registered successfully")
                .build();
        }
 }
