package com.example.OnlineFoodOrderingSystem.service.intf;

import com.example.OnlineFoodOrderingSystem.entities.IngredientsCategory;
import com.example.OnlineFoodOrderingSystem.entities.IngredientsItem;

import java.util.List;

public interface  IngredientService {

    IngredientsCategory createIngredientCategory(String name, long restaurantId) throws Exception;

    IngredientsCategory getIngredientCategoryById(long categoryId) throws Exception;

    List<IngredientsCategory> getIngredientCategoryByRestaurantId(long restaurantId) throws Exception;

    IngredientsItem createIngredientItem(long restaurantId,String ingredientName,long categoryId)throws Exception;

    List<IngredientsItem> getRestaurantsIngredients(long restaurantId) throws Exception;

    IngredientsItem updateStock(long id)throws Exception;
}
