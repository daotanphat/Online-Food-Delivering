package com.phat.food_delivering.dto;

import java.util.List;

public record CartItemDTO(
        Long id,
        FoodDTO foodDTO,
        int quantity,
        List<String> ingredients,
        Long totalPrice
) {
}
