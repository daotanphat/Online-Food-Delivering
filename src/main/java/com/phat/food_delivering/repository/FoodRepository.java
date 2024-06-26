package com.phat.food_delivering.repository;

import com.phat.food_delivering.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    void deleteById(Long id);

    Optional<Food> getFoodById(Long id);

    Food save(Food food);

    List<Food> getFoodByRestaurantId(Long restaurantId);

    @Query("SELECT f FROM Food f" +
            " WHERE f.name LIKE %:key%" +
            " OR f.foodCategory.name LIKE %:key%")
    List<Food> getFoodByKey(@Param("key") String key);
}
