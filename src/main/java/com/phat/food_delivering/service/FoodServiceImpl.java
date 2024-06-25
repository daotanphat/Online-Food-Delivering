package com.phat.food_delivering.service;

import com.phat.food_delivering.exception.EntityNotFoundException;
import com.phat.food_delivering.model.Category;
import com.phat.food_delivering.model.Food;
import com.phat.food_delivering.model.Restaurant;
import com.phat.food_delivering.repository.FoodRepository;
import com.phat.food_delivering.request.CreateFoodRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {
    @Autowired
    FoodRepository foodRepository;

    @Override
    public Food createFood(CreateFoodRequest request, Category category, Restaurant restaurant) {
        Food food = new Food();
        food.setId(request.getId());
        food.setName(request.getName());
        food.setDescription(request.getDescription());
        food.setPrice(request.getPrice());
        food.setFoodCategory(request.getCategory());
        food.setImages(request.getImages());
        food.setAvailable(request.isAvailable());
        food.setRestaurant(restaurant);
        food.setVegetarian(request.isVegetarian());
        food.setSeasonal(request.isSeasonal());
        food.setIngredients(request.getIngredients());
        food.setCreationDate(new Date(new Date().getTime()));

        return foodRepository.save(food);
    }

    @Override
    public Food updateFood(CreateFoodRequest request, Category category, Restaurant restaurant, Long id) {
        Food food = getFoodById(id);
        food.setName(request.getName());
        food.setDescription(request.getDescription());
        food.setPrice(request.getPrice());
        food.setFoodCategory(category);
        food.setImages(request.getImages());
        food.setAvailable(request.isAvailable());
        food.setVegetarian(request.isVegetarian());
        food.setSeasonal(request.isSeasonal());
        food.setIngredients(request.getIngredients());
        return foodRepository.save(food);
    }

    @Override
    public void deleteFood(Long id) {
        Food food = getFoodById(id);
        food.setRestaurant(null);
        foodRepository.save(food);
    }

    @Override
    public Food getFoodById(Long id) {
        Optional<Food> food = foodRepository.getFoodById(id);
        return unwrappedFood(food, id);
    }

    @Override
    public List<Food> getFoodByRestaurantId(Long restaurantId) {
        return foodRepository.getFoodByRestaurantId(restaurantId);
    }

    @Override
    public Food updateFoodStatus(Long id) {
        Food food = getFoodById(id);
        food.setAvailable(!food.isAvailable());
        return foodRepository.save(food);
    }

    @Override
    public List<Food> getFoodIsvegeterianOrSeasonalAndCategoryIdBasedOnRestauarntId(Long restaurantId, boolean isVegeterian, boolean isSeasonal, String category) {
        List<Food> foods = getFoodByRestaurantId(restaurantId);
        if (isVegeterian) {
            foods = foods.stream().filter(Food::isVegetarian).collect(Collectors.toList());
        }
        if (isSeasonal) {
            foods = foods.stream().filter(Food::isSeasonal).collect(Collectors.toList());
        }
        if (!category.isEmpty() && !category.equals("")) {
            foods = foods.stream().filter(f -> f.getFoodCategory().getName().equals(category)).collect(Collectors.toList());
        }
        return foods;
    }

    @Override
    public List<Food> getFoodBasedOnKeyWord(String keyWord) {
        return foodRepository.getFoodByKey(keyWord);
    }

    static Food unwrappedFood(Optional<Food> entity, Long id) {
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new EntityNotFoundException(Food.class, id);
        }
    }
}
