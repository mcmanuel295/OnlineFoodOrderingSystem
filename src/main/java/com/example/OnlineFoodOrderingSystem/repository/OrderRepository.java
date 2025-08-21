package com.example.OnlineFoodOrderingSystem.repository;

import com.example.OnlineFoodOrderingSystem.entities.Order;
import com.example.OnlineFoodOrderingSystem.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query(value = "SELECT o FROM Order o WHERE o.customer.userId =:userId",nativeQuery = true)
    List<Order> findByCustomer(long userId);

    @Query(value = "SELECT o FROM Order o WHERE o.restaurant.userId =:restaurantId",nativeQuery = true)
    List<Order> findByRestaurant(long restaurantId);

    @Query(value = "SELECT o FROM Order o WHERE",nativeQuery = true)
    List<Order> findestaurantOrderByStatus(Restaurant restaurant, String orderStatus);
}
