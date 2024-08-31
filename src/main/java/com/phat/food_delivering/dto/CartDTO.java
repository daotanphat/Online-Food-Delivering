package com.phat.food_delivering.dto;

import com.phat.food_delivering.model.Cart;

import java.time.LocalDateTime;
import java.util.List;

public record CartDTO(
        Long id,
        List<CartItemDTO> cartItems,
        Long totalPrice
) {
    public static CartDTO noArgConstructor() {
        return new CartDTO(
                null, List.of(), null
        );
    }
}
