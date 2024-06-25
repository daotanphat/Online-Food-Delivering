package com.phat.food_delivering.request;

import com.phat.food_delivering.model.Category;
import com.phat.food_delivering.model.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateFoodRequest {
    private Long id;
    private String name;
    private String description;
    private Long price;
    private Category category;
    private List<String> images;
    private boolean available;
    private Long restaurantId;
    private boolean isVegetarian;
    private boolean isSeasonal;
    private List<IngredientsItem> ingredients;
}
