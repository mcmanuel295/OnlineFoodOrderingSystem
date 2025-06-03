package com.example.OnlineFoodOrderingSystem.service.intf;

import com.example.OnlineFoodOrderingSystem.entities.User;

public interface UserService {

    User findUserByJwtToken(String token);

    User findUserByEmail(String email);
}
