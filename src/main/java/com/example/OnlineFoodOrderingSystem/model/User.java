package com.example.OnlineFoodOrderingSystem.model;

import com.example.OnlineFoodOrderingSystem.dto.RestaurantDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String fullName;
    private String email;
    private String password;
    private USER_ROLE role;

    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true
    )
    private List<Order> order = new ArrayList<>();

    @ElementCollection
    private List<RestaurantDto> favourite = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    private  String string;

}
