package com.example.OnlineFoodOrderingSystem.pojo;

import com.example.OnlineFoodOrderingSystem.entities.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String jwt;
    private User user;
    private String message;
    private USER_ROLE role;
}
