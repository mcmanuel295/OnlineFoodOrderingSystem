package com.example.OnlineFoodOrderingSystem.controller;

import com.example.OnlineFoodOrderingSystem.entities.Category;
import com.example.OnlineFoodOrderingSystem.entities.Food;
import com.example.OnlineFoodOrderingSystem.entities.Restaurant;
import com.example.OnlineFoodOrderingSystem.request.CreateFoodRequest;
import com.example.OnlineFoodOrderingSystem.service.intf.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AdminFoodController {
    private final FoodService foodService;


    @PostMapping("/")
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest foodRequest, Category category, Restaurant restaurant){
        return new ResponseEntity<>(foodService.createFood(foodRequest,category,restaurant), HttpStatus.CREATED);
    }

    @PutMapping("/{foodId}/updateStatus")
    public ResponseEntity<Food> foodAvailability(@PathVariable long foodId){
        return new ResponseEntity<>(foodService.updateAvailabilityStatus(foodId), HttpStatus.OK);
    }

    @PutMapping("/{foodId}")
    public ResponseEntity<Food> updateFood(@PathVariable long foodId, Food updatedFood){
        return new ResponseEntity<>(foodService.updateFood(foodId,updatedFood), HttpStatus.OK);
    }

    @DeleteMapping("/{foodId}")
    public ResponseEntity<Food> deleteFood(@PathVariable long foodId){
        foodService.deleteFood(foodId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
