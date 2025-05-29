package com.example.OnlineFoodOrderingSystem.service;

import com.example.OnlineFoodOrderingSystem.model.User;
import com.example.OnlineFoodOrderingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(username).orElseThrow( ()-> new UsernameNotFoundException("User Not Found With Email "));
        return new MyUserDetails(user);

    }
}
