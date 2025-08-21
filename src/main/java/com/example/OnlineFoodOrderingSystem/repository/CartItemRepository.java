package com.example.OnlineFoodOrderingSystem.repository;

import com.example.OnlineFoodOrderingSystem.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
