package com.example.OnlineFoodOrderingSystem.controller;

import com.example.OnlineFoodOrderingSystem.entities.Restaurant;
import com.example.OnlineFoodOrderingSystem.entities.User;
import com.example.OnlineFoodOrderingSystem.request.RestaurantRequest;
import com.example.OnlineFoodOrderingSystem.service.intf.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody RestaurantRequest restaurantRequest, User user){
        return new ResponseEntity<>(restaurantService.createRestaurant(restaurantRequest,user), HttpStatus.CREATED);
    }
}
