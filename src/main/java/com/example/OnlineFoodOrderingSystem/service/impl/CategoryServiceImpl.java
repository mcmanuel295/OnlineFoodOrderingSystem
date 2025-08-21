package com.example.OnlineFoodOrderingSystem.service.impl;

import com.example.OnlineFoodOrderingSystem.entities.Category;
import com.example.OnlineFoodOrderingSystem.entities.Restaurant;
import com.example.OnlineFoodOrderingSystem.entities.User;
import com.example.OnlineFoodOrderingSystem.repository.CategoryRepository;
import com.example.OnlineFoodOrderingSystem.service.intf.CategoryService;
import com.example.OnlineFoodOrderingSystem.service.intf.RestaurantService;
import com.example.OnlineFoodOrderingSystem.service.intf.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepo;
    private final RestaurantService restaurantService;
    private final UserService userService;


    @Override
    public Category createCategory(String name, String jwt) {
        User user = userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.getRestaurantByOwnerId(user.getUserId());
        if (restaurant == null) {
            throw new EntityNotFoundException("Restaurant not found!!!");
        }

        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurant);

        return categoryRepo.save(category);

    }

    @Override
    public List<Category> findCategoryByRestaurantId(long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (restaurant == null) {
            throw new EntityNotFoundException("Restaurant not found!!!");
        }

        return categoryRepo.findCategoryByRestaurantId(restaurantId);
    }

    @Override
    public Category findCategoryById(long categoryId) {
        if(categoryRepo.findById(categoryId).isEmpty()){
            throw new EntityNotFoundException("Category not found");
        }
        return categoryRepo.findById(categoryId).get();
    }
}
