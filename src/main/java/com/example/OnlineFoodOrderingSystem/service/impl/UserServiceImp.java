package com.example.OnlineFoodOrderingSystem.service.impl;

import com.example.OnlineFoodOrderingSystem.entities.User;
import com.example.OnlineFoodOrderingSystem.repository.UserRepository;
import com.example.OnlineFoodOrderingSystem.service.JwtService;
import com.example.OnlineFoodOrderingSystem.service.intf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepo;
    private final JwtService jwtService;

    public User findUserByJwtToken(String token){
        String username = jwtService.extractUsername(token);
        return userRepo.findByEmail(username).orElseThrow(()->
                new UsernameNotFoundException("user "+username+" not found"));
    }

    @Override
    public User findUserByEmail(String email) {
        if(userRepo.findByEmail(email).isEmpty()){
            throw new UsernameNotFoundException("user "+email+" not found");
        }
        return userRepo.findByEmail(email).get();
    }
}
