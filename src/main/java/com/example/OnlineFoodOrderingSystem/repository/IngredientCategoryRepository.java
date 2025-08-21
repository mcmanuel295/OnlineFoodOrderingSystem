package com.example.OnlineFoodOrderingSystem.repository;

import com.example.OnlineFoodOrderingSystem.entities.IngredientsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredientCategoryRepository extends JpaRepository<IngredientsCategory,Long> {

    @Query("SELECT i FROM IngredientsCategory i WHERE i.restaurant.restaurantId =:id")
    List<IngredientsCategory> findByRestaurantId(long id);
}
