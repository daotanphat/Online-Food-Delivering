package com.phat.food_delivering.dto;

public record IngredientDTO(
        Long id,
        String name,
        String categoryName,
        boolean status
) {
}
