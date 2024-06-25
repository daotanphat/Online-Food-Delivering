package com.phat.food_delivering.service;

import com.phat.food_delivering.model.Category;
import com.phat.food_delivering.model.Food;
import com.phat.food_delivering.model.Restaurant;
import com.phat.food_delivering.request.CreateFoodRequest;

import java.util.List;

public interface FoodService {
    public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant);

    public Food updateFood(CreateFoodRequest request, Category category, Restaurant restaurant, Long id);

    public void deleteFood(Long id);

    public Food getFoodById(Long id);

    public List<Food> getFoodByRestaurantId(Long restaurantId);

    public Food updateFoodStatus(Long id);

    public List<Food> getFoodIsvegeterianOrSeasonalAndCategoryIdBasedOnRestauarntId(
            Long restaurantId,
            boolean isVegeterian,
            boolean isSeasonal,
            String category);

    public List<Food> getFoodBasedOnKeyWord(String keyWord);
}
