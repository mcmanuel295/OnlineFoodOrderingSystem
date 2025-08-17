package com.example.OnlineFoodOrderingSystem.controller;

import com.example.OnlineFoodOrderingSystem.entities.IngredientsCategory;
import com.example.OnlineFoodOrderingSystem.entities.IngredientsItem;
import com.example.OnlineFoodOrderingSystem.request.IngredientCategoryRequest;
import com.example.OnlineFoodOrderingSystem.request.IngredientRequest;
import com.example.OnlineFoodOrderingSystem.service.intf.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {
    private IngredientService ingredientService;

    @PostMapping("/category")
    public ResponseEntity<IngredientsCategory> createIngredientCategory(@RequestBody IngredientCategoryRequest categoryRequest) throws Exception {

        IngredientsCategory item = ingredientService.createIngredientCategory(categoryRequest.getName(),categoryRequest.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping("/")
    public ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody IngredientRequest req) throws Exception {

        return new ResponseEntity<>(ingredientService.createIngredientItem(req.getRestaurantId(),req.getName(),req.getCategoryId()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<IngredientsItem> updateIngredientStock(@PathVariable long id) throws Exception {

        return new ResponseEntity<>(ingredientService.updateStock(id),HttpStatus.CREATED);
    }

    @GetMapping("/{restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> restaurantIngredient(@PathVariable long id) throws Exception {

        return new ResponseEntity<>(ingredientService.getRestaurantsIngredients(id), HttpStatus.CREATED);
    }
}
