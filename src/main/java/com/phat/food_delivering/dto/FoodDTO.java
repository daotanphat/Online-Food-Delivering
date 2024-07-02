package com.phat.food_delivering.dto;

import com.phat.food_delivering.model.Category;
import com.phat.food_delivering.model.IngredientsItem;
import com.phat.food_delivering.model.Restaurant;

import java.util.Date;
import java.util.List;

public record FoodDTO(
        Long id,
        String name,
        String description,
        Long price,
        CategoryDTO categoryDTO,
        List<String> images,
        boolean available,
        Long restaurantId,
        boolean isVegetarian,
        boolean isSeasonal,
        List<IngredientDTO> ingredients,
        Date creationDate
) {
}
