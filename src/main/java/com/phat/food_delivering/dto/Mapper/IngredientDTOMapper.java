package com.phat.food_delivering.dto.Mapper;

import com.phat.food_delivering.dto.IngredientDTO;
import com.phat.food_delivering.model.IngredientCategory;
import com.phat.food_delivering.model.IngredientsItem;
import com.phat.food_delivering.model.Restaurant;
import com.phat.food_delivering.repository.IngredientCategoryRepository;
import com.phat.food_delivering.request.CreateIngredientItemRequest;
import com.phat.food_delivering.service.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class IngredientDTOMapper implements Function<IngredientsItem, IngredientDTO> {

    @Override
    public IngredientDTO apply(IngredientsItem ingredientsItem) {
        return new IngredientDTO(
                ingredientsItem.getId(),
                ingredientsItem.getName(),
                ingredientsItem.getCategory().getName(),
                ingredientsItem.isInStock()
        );
    }

    public IngredientsItem toIngredientItem(CreateIngredientItemRequest request) {
        IngredientsItem ingredientsItem = new IngredientsItem();
        ingredientsItem.setName(request.getName());

        return ingredientsItem;
    }
}
