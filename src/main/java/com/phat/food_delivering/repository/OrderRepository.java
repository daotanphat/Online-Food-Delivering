package com.phat.food_delivering.repository;

import com.phat.food_delivering.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrderByCustomerIdOrderByCreateAtDesc(long customerId);

    List<Order> findOrderByRestaurantId(Long restaurantId);
}
