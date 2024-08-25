package com.phat.food_delivering.service;

import com.phat.food_delivering.dto.FoodDTO;
import com.phat.food_delivering.model.Category;
import com.phat.food_delivering.model.Food;
import com.phat.food_delivering.model.Restaurant;
import com.phat.food_delivering.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {
    public FoodDTO createFood(CreateFoodRequest request, String token);

    public FoodDTO updateFood(CreateFoodRequest request, Long id);

    public void deleteFood(Long id);

    public Food getFoodById(Long id);

    public List<FoodDTO> getFoodByRestaurantId(Long restaurantId);

    public FoodDTO updateFoodStatus(Long id);

    public List<FoodDTO> getFoodIsvegeterianOrSeasonalAndCategoryIdBasedOnRestauarntId(
            Long restaurantId,
            boolean isVegeterian,
            boolean isSeasonal,
            String category);

    public List<FoodDTO> getFoodBasedOnKeyWord(String keyWord);
}
