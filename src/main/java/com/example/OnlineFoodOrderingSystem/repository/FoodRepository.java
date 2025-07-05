package com.example.OnlineFoodOrderingSystem.repository;

import com.example.OnlineFoodOrderingSystem.entities.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food,Long> {

    Optional<Food> findByFoodId(Long foodId);

    List<Food> findByRestaurantId();

    @Query("SELECT f FROM Food f WHERE f.name LIKE %:keyword% OR f,foodCategory.name LIKE %:keyword%")
    List<Food> searchFood(@Param("keyword") String query);
}
