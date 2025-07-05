package com.example.OnlineFoodOrderingSystem.repository;

import com.example.OnlineFoodOrderingSystem.entities.Restaurant;
import com.example.OnlineFoodOrderingSystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    @Query("SELECT r FROM Restaurant r WHERE lower(r.name) LIKE lower(concat('%', :query, '%')) OR lower(r.cuisineType) LIKE lower(concat('%',:query,'%'))")
    List<Restaurant> findBySearchQuery(String query);


    Optional<Restaurant> findByOwner(User user);

    Optional<Restaurant> findById(long restaurantId);
}
