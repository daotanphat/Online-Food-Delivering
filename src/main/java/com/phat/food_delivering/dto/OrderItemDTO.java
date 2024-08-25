package com.phat.food_delivering.dto;

import java.util.List;

public record OrderItemDTO(
        Long id,
        FoodDTO food,
        int quantity,
        Long totalPrice,
        List<String> ingredients
) {
}
