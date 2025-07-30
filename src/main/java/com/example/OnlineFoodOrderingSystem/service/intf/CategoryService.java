package com.example.OnlineFoodOrderingSystem.service.intf;

import com.example.OnlineFoodOrderingSystem.entities.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(String name,long userId);

    List<Category> findCategoryByRestaurantId(long restaurantId);

    Category findCategoryById(long id);

}
