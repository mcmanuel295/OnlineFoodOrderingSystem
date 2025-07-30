package com.example.OnlineFoodOrderingSystem.service.intf;

import com.example.OnlineFoodOrderingSystem.entities.Category;
import com.example.OnlineFoodOrderingSystem.entities.Food;
import com.example.OnlineFoodOrderingSystem.entities.Restaurant;
import com.example.OnlineFoodOrderingSystem.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {

    Food createFood(CreateFoodRequest foodRequest, Category category, Restaurant restaurant);

    Food findById(long foodId);

    List<Food> getRestaurantFood(long restaurantId,
                                 boolean isVegetarian,
                                 boolean nonVegetarian,
                                 boolean isSeasonal,
                                 String foodCategory);

    List<Food> searchFood(String keyword);

    Food updateFood(long foodId, Food updatedFood);

    Food updateAvailabilityStatus(long foodId);

    void deleteFood(long foodId);
}
