package com.phat.food_delivering.repository;

import com.phat.food_delivering.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByRestaurantId(Long restaurantId);

    @Query("SELECT c FROM Category c" +
            " WHERE c.name LIKE %:search%")
    List<Category> getCategoriesBySearch(@Param("search") String search);
}
