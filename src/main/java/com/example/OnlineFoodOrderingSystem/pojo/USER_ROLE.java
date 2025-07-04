package com.example.OnlineFoodOrderingSystem.pojo;

public enum USER_ROLE {
    ROLE_CUSTOMER ("customer"),
    ROLE_RESTAURANT_OWNER ("owner"),
    ROLE_ADMIN("admin"),
    ROLE_USER("user");

    private final String role;

    USER_ROLE(String role) {
        this.role= role;
    }
}
