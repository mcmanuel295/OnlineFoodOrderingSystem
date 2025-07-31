package com.example.OnlineFoodOrderingSystem.controller;

import com.example.OnlineFoodOrderingSystem.entities.Category;
import com.example.OnlineFoodOrderingSystem.entities.Restaurant;
import com.example.OnlineFoodOrderingSystem.service.intf.CategoryService;
import com.example.OnlineFoodOrderingSystem.service.intf.RestaurantService;
import com.example.OnlineFoodOrderingSystem.service.intf.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final RestaurantService restaurantService;
    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<Category> createCategory(@RequestBody Category category, @RequestHeader("Authorization") String jwt){
        return new ResponseEntity<>( categoryService.createCategory(category.getName(),jwt), HttpStatus.CREATED);
    }

    @GetMapping("/category/restaurant")
    public ResponseEntity<List<Category>> getRestaurantCategory(@RequestHeader("Authorization") String jwt){
        Restaurant restaurant = restaurantService.getRestaurantByOwnerId(userService.findUserByJwtToken(jwt).getUserId());

        return new ResponseEntity<>(categoryService.findCategoryByRestaurantId(restaurant.getRestaurantId()),HttpStatus.OK);
    }
}
