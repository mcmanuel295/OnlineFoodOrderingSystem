package com.example.OnlineFoodOrderingSystem.repository;

import com.example.OnlineFoodOrderingSystem.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart,Long> {

    @Query(value = "SELECT C FROM c WHERE c.user.userId =:userId",nativeQuery = true)
    Cart findByUserId(long userId);

}
