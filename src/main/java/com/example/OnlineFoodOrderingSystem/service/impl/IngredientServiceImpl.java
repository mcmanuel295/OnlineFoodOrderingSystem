package com.example.OnlineFoodOrderingSystem.service.impl;

import com.example.OnlineFoodOrderingSystem.entities.IngredientsCategory;
import com.example.OnlineFoodOrderingSystem.entities.IngredientsItem;
import com.example.OnlineFoodOrderingSystem.entities.Restaurant;
import com.example.OnlineFoodOrderingSystem.repository.IngredientCategoryRepository;
import com.example.OnlineFoodOrderingSystem.repository.IngredientsItemRepository;
import com.example.OnlineFoodOrderingSystem.service.intf.IngredientService;
import com.example.OnlineFoodOrderingSystem.service.intf.RestaurantService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    private final IngredientCategoryRepository ingredientCategoryRepo;
    private final IngredientsItemRepository ingredientsItemRepo;
    private final RestaurantService restaurantService;

    @Override
    public IngredientsCategory createIngredientCategory(String name, long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        IngredientsCategory ingredientsCategory = new IngredientsCategory();
        ingredientsCategory.setRestaurant(restaurant);
        ingredientsCategory.setName(name);

        return ingredientCategoryRepo.save(ingredientsCategory);

    }

    @Override
    public IngredientsCategory getIngredientCategoryById(long categoryId) throws Exception {
        Optional<IngredientsCategory> ingredientsCategory = ingredientCategoryRepo.findById(categoryId);

        if (ingredientsCategory.isEmpty()) {
            throw new EntityNotFoundException("Ingredient Category not found");
        }

        return ingredientsCategory.get();
    }

    @Override
    public List<IngredientsCategory> getIngredientCategoryByRestaurantId(long restaurantId) throws Exception {
        if(restaurantService.getRestaurantById(restaurantId)==null){
            throw new EntityNotFoundException("Restaurant Not Found");
        }
        return ingredientCategoryRepo.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem createIngredientItem(long restaurantId, String ingredientName, long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if(restaurant ==null){
            throw new EntityNotFoundException("Restaurant Not Found");
        }

        IngredientsCategory category = getIngredientCategoryById(categoryId);

        IngredientsItem ingredient = new IngredientsItem();
        ingredient.setName(ingredientName);
        ingredient.setRestaurant(restaurant);
        ingredient.setCategory(category);
        category.getIngredientsItems().add(ingredient);

        return ingredient;
    }

    @Override
    public List<IngredientsItem> getRestaurantsIngredients(long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if(restaurant ==null){
            throw new EntityNotFoundException("Restaurant Not Found");
        }

        return List.of();
    }

    @Override
    public IngredientsItem updateStock(long id) throws Exception {
        return null;
    }
}
