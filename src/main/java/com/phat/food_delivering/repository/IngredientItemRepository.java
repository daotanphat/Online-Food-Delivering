package com.phat.food_delivering.repository;

import com.phat.food_delivering.model.IngredientsItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientItemRepository extends JpaRepository<IngredientsItem, Long> {
    List<IngredientsItem> findByRestaurantId(Long restaurantId);

    @Query(
            nativeQuery = true,
            value = "SELECT i1.id, i1.in_stock, i1.name, i1.category_id, i1.restaurant_id\n" +
                    "FROM ingredients_item AS i1\n" +
                    "JOIN ingredient_food AS i2 ON i1.id = i2.ingredient_id\n" +
                    "WHERE i2.food_id = :foodId"
    )
    List<IngredientsItem> findByFoodId(@Param("foodId") Long foodId);
}
