package com.phat.food_delivering.service;

import com.phat.food_delivering.model.IngredientCategory;
import com.phat.food_delivering.model.IngredientsItem;

import java.util.List;

public interface IngredientService {
    public IngredientCategory createIngredientCategory(String name, Long restaurantId);

    public IngredientCategory getIngredientCategoryById(Long id);

    public List<IngredientCategory> getIngredientCategoryByRestaurantId(Long restaurantId);

    public IngredientsItem createIngredientItem(String name, Long categoryId, Long restaurantId);

    public IngredientsItem getIngredientItemById(Long id);

    public List<IngredientsItem> getIngredientItemByRestaurantId(Long restaurantId);

    public IngredientsItem updateIngredientItemStock(Long id);
}
