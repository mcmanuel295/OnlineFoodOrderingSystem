package com.example.OnlineFoodOrderingSystem.controller;

import com.example.OnlineFoodOrderingSystem.entities.Food;
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

    @GetMapping("/{foodId}")
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




}
