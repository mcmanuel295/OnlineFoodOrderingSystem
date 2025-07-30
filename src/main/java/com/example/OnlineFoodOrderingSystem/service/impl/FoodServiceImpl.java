package com.example.OnlineFoodOrderingSystem.service.impl;

import com.example.OnlineFoodOrderingSystem.entities.Category;
import com.example.OnlineFoodOrderingSystem.entities.Food;
import com.example.OnlineFoodOrderingSystem.entities.Restaurant;
import com.example.OnlineFoodOrderingSystem.repository.FoodRepository;
import com.example.OnlineFoodOrderingSystem.repository.RestaurantRepository;
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
    private final RestaurantRepository restaurantRepo;


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

        food.get().setRestaurant(null);
        foodRepo.save(food.get());
    }

    @Override
    public List<Food> getRestaurantFood(long restaurantId, boolean isVegetarian, boolean nonVegetarian, boolean isSeasonal, String foodCategory) {
        Optional<Restaurant> restaurant = restaurantRepo.findById(restaurantId);
        if (restaurant.isEmpty()) {
            throw new EntityNotFoundException("Restaurant with id "+restaurantId+" not found");
        }



        return restaurant.get()
                .getFoods()
                .stream()
                .filter(food -> food.isVegetarian()== isVegetarian ^ food.isVegetarian()== nonVegetarian ^ food.isSeasonal()==isSeasonal ^ food.getFoodCategory().getName().equals(foodCategory))
                .toList();
    }


    @Override
    public List<Food> searchFood(String keyword) {
        return foodRepo.searchFood(keyword);
    }

    @Override
    public Food findById(long foodId) {
        Optional<Food> food = foodRepo.findById(foodId);
        if (food.isEmpty()) {
            throw new EntityNotFoundException("Food with id "+foodId+" not found");
        }

        return food.get();
    }

    @Override
    public Food updateAvailabilityStatus(long foodId) {
        Optional<Food> food = foodRepo.findByFoodId(foodId);

        if (food.isEmpty()) {
            throw new EntityNotFoundException("Food with the user Id "+foodId+" not found");
        }
        food.get().setAvailable(!(food.get().isAvailable()));

        return foodRepo.save(food.get());
    }

    @Override
    public Food updateFood(long foodId, Food updatedFood) {
        Optional<Food> food = foodRepo.findByFoodId(foodId);

        if (food.isEmpty() ) {
            throw  new EntityNotFoundException("Food with Id "+foodId+" not found");
        }

        updatedFood.setFoodId(foodId);
        return foodRepo.save(updatedFood);
    }
}
