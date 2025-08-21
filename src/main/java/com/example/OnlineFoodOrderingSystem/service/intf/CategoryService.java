package com.example.OnlineFoodOrderingSystem.service.intf;

import com.example.OnlineFoodOrderingSystem.entities.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(String name,String jwt);

    List<Category> findCategoryByRestaurantId(long restaurantId) throws Exception;

    Category findCategoryById(long categoryId);

}
