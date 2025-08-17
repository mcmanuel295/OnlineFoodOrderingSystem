package com.example.OnlineFoodOrderingSystem.repository;

import com.example.OnlineFoodOrderingSystem.entities.IngredientsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredientCategoryRepository extends JpaRepository<IngredientsCategory,Long> {

    @Query("SELECT i FROM IngredientCategory i WHERE i.restaurant.id = :restaurantId")
    List<IngredientsCategory> findByRestaurantId(long restaurantId);
}
