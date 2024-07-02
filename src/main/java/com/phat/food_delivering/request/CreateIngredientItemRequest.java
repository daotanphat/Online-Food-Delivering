package com.phat.food_delivering.request;

import lombok.Data;

@Data
public class CreateIngredientItemRequest {
    private String name;
    private Long categoryId;
}
