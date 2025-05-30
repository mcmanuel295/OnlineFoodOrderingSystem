package com.example.OnlineFoodOrderingSystem.repository;

import com.example.OnlineFoodOrderingSystem.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
