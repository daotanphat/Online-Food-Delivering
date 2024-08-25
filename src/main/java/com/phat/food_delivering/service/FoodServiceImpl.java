package com.phat.food_delivering.service;

import com.phat.food_delivering.dto.FoodDTO;
import com.phat.food_delivering.dto.Mapper.FoodDTOMapper;
import com.phat.food_delivering.dto.Mapper.RestaurantDTOMapper;
import com.phat.food_delivering.dto.RestaurantDTOO;
import com.phat.food_delivering.exception.EntityNotFoundException;
import com.phat.food_delivering.model.*;
import com.phat.food_delivering.repository.CategoryRepository;
import com.phat.food_delivering.repository.FoodRepository;
import com.phat.food_delivering.request.CreateFoodRequest;
import com.phat.food_delivering.security.SecurityConstants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FoodServiceImpl implements FoodService {
    FoodRepository foodRepository;
    IngredientService ingredientService;
    CategoryRepository categoryRepository;
    FoodDTOMapper foodDTOMapper;
    UserService userService;
    RestaurantService restaurantService;
    RestaurantDTOMapper restaurantDTOMapper;

    @Override
    public FoodDTO createFood(CreateFoodRequest request, String token) {
        token = token.replace(SecurityConstants.BEARER, "");
        User user = userService.findUserBasedOnToken(token);
        RestaurantDTOO restaurantDTOO = restaurantService.findRestaurantByUserId(user.getId());
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantDTOO.id());
        Food food = new Food();
        food = foodDTOMapper.toFood(request);
        food.setRestaurant(restaurant);
        food.setCreationDate(new Date());
        Food foodsaved = foodRepository.save(food);

        for (IngredientsItem ingredientsItem : food.getIngredients()) {
            ingredientsItem.getFoods().add(food);
            ingredientService.save(ingredientsItem);
        }
        return foodDTOMapper.apply(foodsaved);
    }

    @Override
    public FoodDTO updateFood(CreateFoodRequest request, Long id) {
        Food food = getFoodById(id);
        food = foodDTOMapper.toFood(request);
        return foodDTOMapper.apply(foodRepository.save(food));
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
    public List<FoodDTO> getFoodByRestaurantId(Long restaurantId) {
        return foodRepository.getFoodByRestaurantId(restaurantId)
                .stream()
                .map(foodDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public FoodDTO updateFoodStatus(Long id) {
        Food food = getFoodById(id);
        food.setAvailable(!food.isAvailable());
        return foodDTOMapper.apply(foodRepository.save(food));
    }

    @Override
    public List<FoodDTO> getFoodIsvegeterianOrSeasonalAndCategoryIdBasedOnRestauarntId(Long restaurantId, boolean isVegeterian, boolean isSeasonal, String category) {
        List<FoodDTO> foods = getFoodByRestaurantId(restaurantId);
        if (isVegeterian) {
            foods = foods.stream().filter((food) -> food.isVegetarian() && !food.isSeasonal()).collect(Collectors.toList());
        } else if (isSeasonal) {
            foods = foods.stream().filter((food) -> !food.isVegetarian() && food.isSeasonal()).collect(Collectors.toList());
        }
        if (!category.isEmpty() && !category.equals("")) {
            foods = foods.stream().filter(f -> f.categoryDTO().name().toLowerCase().contains(category.toLowerCase())).collect(Collectors.toList());
        }
        return foods;
    }

    @Override
    public List<FoodDTO> getFoodBasedOnKeyWord(String keyWord) {
        return foodRepository.getFoodByKey(keyWord)
                .stream()
                .map(foodDTOMapper)
                .collect(Collectors.toList());
    }

    static Food unwrappedFood(Optional<Food> entity, Long id) {
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new EntityNotFoundException(Food.class, id);
        }
    }
}
