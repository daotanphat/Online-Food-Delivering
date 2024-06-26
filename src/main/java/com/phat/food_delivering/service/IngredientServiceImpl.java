package com.phat.food_delivering.service;

import com.phat.food_delivering.exception.EntityNotFoundException;
import com.phat.food_delivering.model.IngredientCategory;
import com.phat.food_delivering.model.IngredientsItem;
import com.phat.food_delivering.model.Restaurant;
import com.phat.food_delivering.repository.IngredientCategoryRepository;
import com.phat.food_delivering.repository.IngredientItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {
    IngredientCategoryRepository ingredientCategoryRepository;
    IngredientItemRepository ingredientItemRepository;
    RestaurantService restaurantService;

    @Autowired
    public IngredientServiceImpl(IngredientCategoryRepository ingredientCategoryRepository, IngredientItemRepository ingredientItemRepository, RestaurantService restaurantService) {
        this.ingredientItemRepository = ingredientItemRepository;
        this.ingredientCategoryRepository = ingredientCategoryRepository;
        this.restaurantService = restaurantService;
    }

    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) {
        IngredientCategory ingredientCategory = new IngredientCategory();
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        ingredientCategory.setName(name);
        ingredientCategory.setRestaurant(restaurant);
        return ingredientCategoryRepository.save(ingredientCategory);
    }

    @Override
    public IngredientCategory getIngredientCategoryById(Long id) {
        Optional<IngredientCategory> ingredientCategory = ingredientCategoryRepository.findById(id);
        return unwrapIngredientCategory(ingredientCategory, id);
    }

    @Override
    public List<IngredientCategory> getIngredientCategoryByRestaurantId(Long restaurantId) {
        return ingredientCategoryRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem createIngredientItem(String name, Long categoryId, Long restaurantId) {
        IngredientsItem ingredientsItem = new IngredientsItem();
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        ingredientsItem.setName(name);
        ingredientsItem.setCategory(getIngredientCategoryById(categoryId));
        ingredientsItem.setRestaurant(restaurant);
        return ingredientItemRepository.save(ingredientsItem);
    }

    @Override
    public IngredientsItem getIngredientItemById(Long id) {
        Optional<IngredientsItem> ingredientsItem = ingredientItemRepository.findById(id);
        return unwrapIngredientsItem(ingredientsItem, id);
    }

    @Override
    public List<IngredientsItem> getIngredientItemByRestaurantId(Long restaurantId) {
        return ingredientItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem updateIngredientItemStock(Long id) {
        IngredientsItem ingredientsItem = getIngredientItemById(id);
        ingredientsItem.setInStock(!ingredientsItem.isInStock());
        return ingredientItemRepository.save(ingredientsItem);
    }

    static IngredientCategory unwrapIngredientCategory(Optional<IngredientCategory> entity, Long id) {
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new EntityNotFoundException(IngredientCategory.class, id);
        }
    }

    static IngredientsItem unwrapIngredientsItem(Optional<IngredientsItem> entity, Long id) {
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new EntityNotFoundException(IngredientsItem.class, id);
        }
    }
}
