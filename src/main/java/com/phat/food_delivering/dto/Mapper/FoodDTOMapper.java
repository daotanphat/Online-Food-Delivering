package com.phat.food_delivering.dto.Mapper;

import com.phat.food_delivering.dto.CategoryDTO;
import com.phat.food_delivering.dto.FoodDTO;
import com.phat.food_delivering.dto.IngredientDTO;
import com.phat.food_delivering.model.Category;
import com.phat.food_delivering.model.Food;
import com.phat.food_delivering.model.IngredientsItem;
import com.phat.food_delivering.repository.CategoryRepository;
import com.phat.food_delivering.repository.FoodRepository;
import com.phat.food_delivering.request.CreateFoodRequest;
import com.phat.food_delivering.service.CategoryService;
import com.phat.food_delivering.service.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class FoodDTOMapper implements Function<Food, FoodDTO> {
    FoodRepository foodRepository;
    CategoryService categoryService;
    IngredientService ingredientService;
    CategoryDTOMapper categoryDTOMapper;
    RestaurantDTOMapper restaurantDTOMapper;
    IngredientDTOMapper ingredientDTOMapper;

    @Override
    public FoodDTO apply(Food food) {
        List<IngredientDTO> ingredientDTOS = new ArrayList<>();
        for (IngredientsItem ingredientsItem : food.getIngredients()) {
            IngredientDTO ingredientDTO = ingredientDTOMapper.apply(ingredientsItem);
            ingredientDTOS.add(ingredientDTO);
        }
        Long restaurantId = (food.getRestaurant() != null) ? food.getRestaurant().getId() : null;
        return new FoodDTO(
                food.getId(),
                food.getName(),
                food.getDescription(),
                food.getPrice(),
                categoryDTOMapper.apply(food.getFoodCategory()),
                food.getImages(),
                food.isAvailable(),
                restaurantId,
                food.isVegetarian(),
                food.isSeasonal(),
                ingredientDTOS,
                food.getCreationDate()
        );
    }

    public Food toFood(CreateFoodRequest request) {
        Category category = categoryService.getCategoryById(request.getCategory());
        List<IngredientsItem> ingredientsItems = new ArrayList<>();
        for (Long ingredientId : request.getIngredients()) {
            IngredientsItem ingredientsItem = ingredientService.getIngredientItemById(ingredientId);
            ingredientsItems.add(ingredientsItem);
        }

        Food food = new Food();
        food.setName(request.getName());
        food.setDescription(request.getDescription());
        food.setPrice(request.getPrice());
        food.setFoodCategory(category);
        food.setImages(request.getImages());
        food.setAvailable(request.isAvailable());
        food.setVegetarian(request.isVegetarian());
        food.setSeasonal(request.isSeasonal());
        food.setIngredients(ingredientsItems);

        return food;
    }
}
