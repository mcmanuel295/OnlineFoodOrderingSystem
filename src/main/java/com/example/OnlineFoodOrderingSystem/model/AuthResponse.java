package com.example.OnlineFoodOrderingSystem.model;

import com.example.OnlineFoodOrderingSystem.entities.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private User user;
    private  String message;
    private USER_ROLE role;
}
