package com.example.OnlineFoodOrderingSystem.repository;

import com.example.OnlineFoodOrderingSystem.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food,Long> {

    Optional<Food> findByFoodId(Long foodId);

    @Query("SELECT f FROM Food f WHERE f.restaurant.restaurantId = :restaurantId")
    List<Food> findByRestaurantId(@Param("restaurantId") long restaurantId);

    @Query("SELECT f FROM Food f WHERE LOWER(f.name) LIKE %:keyword% OR LOWER(f.foodCategory.name) LIKE %:keyword%")
    List<Food> searchFood(@Param("keyword") String query);
}
