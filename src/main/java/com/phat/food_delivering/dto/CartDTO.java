package com.phat.food_delivering.dto;

import java.util.List;

public record CartDTO(
        Long id,
        List<CartItemDTO> cartItems,
        Long totalPrice
) {
}
