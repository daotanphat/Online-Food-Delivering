package com.phat.food_delivering.request;

import lombok.Data;

@Data
public class CreateCategoryRequest {
    private String name;
    private String description;
}
