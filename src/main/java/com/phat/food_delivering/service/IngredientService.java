package com.phat.food_delivering.service;

import com.phat.food_delivering.dto.IngredientCategoryDTO;
import com.phat.food_delivering.dto.IngredientDTO;
import com.phat.food_delivering.model.IngredientCategory;
import com.phat.food_delivering.model.IngredientsItem;
import com.phat.food_delivering.request.CreateIngredientCategoryRequest;
import com.phat.food_delivering.request.CreateIngredientItemRequest;

import java.util.List;

public interface IngredientService {
    public IngredientCategoryDTO createIngredientCategory(CreateIngredientCategoryRequest request, String token);

    public IngredientCategory getIngredientCategoryById(Long id);

    public List<IngredientCategoryDTO> getIngredientCategoryByRestaurantId(Long restaurantId);

    public IngredientDTO createIngredientItem(CreateIngredientItemRequest request, String token);

    public IngredientsItem getIngredientItemById(Long id);

    public List<IngredientDTO> getIngredientItemByRestaurantId(Long restaurantId);

    public IngredientDTO updateIngredientItemStock(Long id);

    public void save(IngredientsItem ingredientsItem);

    public List<IngredientDTO> getIngredientItemByFoodId(Long foodId);
}
