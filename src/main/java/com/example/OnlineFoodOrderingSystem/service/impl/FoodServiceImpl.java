package com.example.OnlineFoodOrderingSystem.service.impl;

import com.example.OnlineFoodOrderingSystem.entities.Category;
import com.example.OnlineFoodOrderingSystem.entities.Food;
import com.example.OnlineFoodOrderingSystem.entities.Restaurant;
import com.example.OnlineFoodOrderingSystem.repository.FoodRepository;
import com.example.OnlineFoodOrderingSystem.request.CreateFoodRequest;
import com.example.OnlineFoodOrderingSystem.service.intf.FoodService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepo;


    @Override
    public Food createFood(CreateFoodRequest foodRequest, Category category, Restaurant restaurant) {
        Food food = new Food();

        food.setName(foodRequest.getName());
        food.setDescription(foodRequest.getDescription());
        food.setPrice(foodRequest.getPrice());
        food.setImages(foodRequest.getImages());
        food.setVegetarian(foodRequest.isVegetarian());
        food.setSeasonal(foodRequest.isSeasonal());
        food.setIngredientsItems(foodRequest.getIngredientsItems());

        food.setFoodCategory(category);
        food.setRestaurant(restaurant);

        Food savedFood = foodRepo.save(food);
        restaurant.getFoods().add(food);
        return savedFood;
    }

    @Override
    public void deleteFood(long foodId) {
        Optional<Food> food = foodRepo.findByFoodId(foodId);

        if (food.isEmpty()) {
            throw new EntityNotFoundException("The Food with foodId "+foodId+" not found");
        }

        foodRepo.deleteById(foodId);
    }

    @Override
    public List<Food> getRestaurantFood(long restaurantId, boolean isVegetarian, boolean nonVegetarian, boolean isSeasonal, String foodCategory) {
        return List.of();
    }

    @Override
    public List<Food> searchFood(String keyword) {
        return List.of();
    }

    @Override
    public Food findById(long foodId) {
        return null;
    }

    @Override
    public Food updateAvailablilityStatus(long foodId) {
        return null;
    }

    @Override
    public Food updateFood(long foodId, Food updatedFood) {
        return null;
    }
}
