package com.example.OnlineFoodOrderingSystem.controller;

import com.example.OnlineFoodOrderingSystem.entities.Restaurant;
import com.example.OnlineFoodOrderingSystem.entities.User;
import com.example.OnlineFoodOrderingSystem.request.RestaurantRequest;
import com.example.OnlineFoodOrderingSystem.service.intf.RestaurantService;
import com.example.OnlineFoodOrderingSystem.service.intf.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {
    private final RestaurantService restaurantService;
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody RestaurantRequest restaurantRequest, @RequestHeader("Authorization") String jwt){
        User user =userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(restaurantService.createRestaurant(restaurantRequest,user), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Restaurant> updateRestaurant(@Valid long restaurantId ,@Valid @RequestBody Restaurant updatedRestaurant){
        return new ResponseEntity<>(restaurantService.updateRestaurant(restaurantId,updatedRestaurant), HttpStatus.CREATED);
    }
}
