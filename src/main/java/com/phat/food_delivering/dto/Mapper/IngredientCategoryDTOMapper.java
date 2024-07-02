package com.phat.food_delivering.dto.Mapper;

import com.phat.food_delivering.dto.IngredientCategoryDTO;
import com.phat.food_delivering.model.IngredientCategory;
import com.phat.food_delivering.request.CreateIngredientCategoryRequest;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class IngredientCategoryDTOMapper implements Function<IngredientCategory, IngredientCategoryDTO> {
    @Override
    public IngredientCategoryDTO apply(IngredientCategory ingredientCategory) {
        return new IngredientCategoryDTO(
                ingredientCategory.getId(),
                ingredientCategory.getName()
        );
    }

    public IngredientCategory toIngredientCategory(CreateIngredientCategoryRequest request) {
        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setName(request.getName());
        return ingredientCategory;
    }
}
