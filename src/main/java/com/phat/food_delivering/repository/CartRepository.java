package com.phat.food_delivering.repository;

import com.phat.food_delivering.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
