package com.phat.food_delivering.repository;

import com.phat.food_delivering.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c" +
            " WHERE c.customer.id = :customerId")
    Cart findByCustomerId(@RequestParam("customerId") Long customerId);
}
