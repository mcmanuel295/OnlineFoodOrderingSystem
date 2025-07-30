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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/foods")
public class FoodController {
    private final FoodService foodService;

    @PostMapping("/")
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest foodRequest, Category category, Restaurant restaurant){
        return new ResponseEntity<>(foodService.createFood(foodRequest,category,restaurant), HttpStatus.CREATED);
    }

    @GetMapping("/{foodId")
    public ResponseEntity<Food> findFoodById(@PathVariable long foodId){
        return new ResponseEntity<>(foodService.findById(foodId), HttpStatus.OK);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(@PathVariable long restaurantId,
                                                        @RequestParam boolean isVegetarian,
                                                        @RequestParam boolean isNonVegetarian,
                                                        @RequestParam boolean isSeasonal,
                                                        @RequestBody String foodCategory){

        return new ResponseEntity<>(foodService.getRestaurantFood(restaurantId,isVegetarian, isNonVegetarian, isSeasonal,foodCategory), HttpStatus.OK);
    }


    @PostMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String keyword){
        return new ResponseEntity<>(foodService.searchFood(keyword), HttpStatus.FOUND);
    }


    @PutMapping("/{foodId}")
    public ResponseEntity<Food> updateFood(@PathVariable long foodId, Food updatedFood){
        return new ResponseEntity<>(foodService.updateFood(foodId,updatedFood), HttpStatus.OK);
    }

    @PutMapping("/{foodId}/updateStatus")
    public ResponseEntity<Food> foodAvailability(@PathVariable long foodId){
        return new ResponseEntity<>(foodService.updateAvailabilityStatus(foodId), HttpStatus.OK);
    }

    @PostMapping("/{foodId}")
    public ResponseEntity<Food> createFood(@PathVariable long foodId){
        foodService.deleteFood(foodId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
