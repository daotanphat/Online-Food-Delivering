package com.phat.food_delivering.service;

import com.phat.food_delivering.dto.IngredientCategoryDTO;
import com.phat.food_delivering.dto.IngredientDTO;
import com.phat.food_delivering.dto.Mapper.IngredientCategoryDTOMapper;
import com.phat.food_delivering.dto.Mapper.IngredientDTOMapper;
import com.phat.food_delivering.exception.EntityNotFoundException;
import com.phat.food_delivering.exception.ListEntityNotFoundException;
import com.phat.food_delivering.model.IngredientCategory;
import com.phat.food_delivering.model.IngredientsItem;
import com.phat.food_delivering.model.Restaurant;
import com.phat.food_delivering.repository.IngredientCategoryRepository;
import com.phat.food_delivering.repository.IngredientItemRepository;
import com.phat.food_delivering.request.CreateIngredientCategoryRequest;
import com.phat.food_delivering.request.CreateIngredientItemRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class IngredientServiceImpl implements IngredientService {
    IngredientCategoryRepository ingredientCategoryRepository;
    IngredientItemRepository ingredientItemRepository;
    RestaurantService restaurantService;
    IngredientDTOMapper ingredientDTOMapper;
    IngredientCategoryDTOMapper ingredientCategoryDTOMapper;

    @Override
    public IngredientCategoryDTO createIngredientCategory(CreateIngredientCategoryRequest request, String token) {
        Restaurant restaurant = restaurantService.findRestaurantByToken(token);
        IngredientCategory ingredientCategory = ingredientCategoryDTOMapper.toIngredientCategory(request);
        ingredientCategory.setRestaurant(restaurant);
        IngredientCategory ingredientCategorySaved = ingredientCategoryRepository.save(ingredientCategory);
        return ingredientCategoryDTOMapper.apply(ingredientCategorySaved);
    }

    @Override
    public IngredientCategory getIngredientCategoryById(Long id) {
        Optional<IngredientCategory> ingredientCategory = ingredientCategoryRepository.findById(id);
        return unwrapIngredientCategory(ingredientCategory, id);
    }

    @Override
    public List<IngredientCategoryDTO> getIngredientCategoryByRestaurantId(Long restaurantId) {
        List<IngredientCategory> ingredientCategorys = ingredientCategoryRepository.findByRestaurantId(restaurantId);
        if (ingredientCategorys.isEmpty()) {
            throw new ListEntityNotFoundException("Not found any ingredient category");
        }
        return ingredientCategorys.stream()
                .map(ingredientCategoryDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public IngredientDTO createIngredientItem(CreateIngredientItemRequest request, String token) {
        Restaurant restaurant = restaurantService.findRestaurantByToken(token);
        IngredientCategory ingredientCategory = getIngredientCategoryById(request.getCategoryId());
        IngredientsItem ingredientsItem = ingredientDTOMapper.toIngredientItem(request);
        ingredientsItem.setCategory(ingredientCategory);
        ingredientsItem.setRestaurant(restaurant);
        IngredientsItem ingredientsItemSaved = ingredientItemRepository.save(ingredientsItem);
        return ingredientDTOMapper.apply(ingredientsItemSaved);
    }

    @Override
    public IngredientsItem getIngredientItemById(Long id) {
        Optional<IngredientsItem> ingredientsItem = ingredientItemRepository.findById(id);
        return unwrapIngredientsItem(ingredientsItem, id);
    }

    @Override
    public List<IngredientDTO> getIngredientItemByRestaurantId(Long restaurantId) {
        List<IngredientsItem> ingredientsItems = ingredientItemRepository.findByRestaurantId(restaurantId);
        if (ingredientsItems.isEmpty()) {
            throw new ListEntityNotFoundException("Not found any ingredient item");
        }
        return ingredientsItems.stream()
                .map(ingredientDTOMapper)
                .collect(Collectors.toList());
    }

    @Override
    public IngredientDTO updateIngredientItemStock(Long id) {
        IngredientsItem ingredientsItem = getIngredientItemById(id);
        ingredientsItem.setInStock(!ingredientsItem.isInStock());
        IngredientsItem ingredientsItemSaved = ingredientItemRepository.save(ingredientsItem);
        return ingredientDTOMapper.apply(ingredientsItemSaved);
    }

    @Override
    public void save(IngredientsItem ingredientsItem) {
        ingredientItemRepository.save(ingredientsItem);
    }

    @Override
    public List<IngredientDTO> getIngredientItemByFoodId(Long foodId) {
        List<IngredientsItem> ingredientsItems = ingredientItemRepository.findByFoodId(foodId);
        List<IngredientDTO> ingredientDTOS = new ArrayList<>();
        for (IngredientsItem ingredientsItem : ingredientsItems) {
            IngredientDTO ingredientDTO = ingredientDTOMapper.apply(ingredientsItem);
            ingredientDTOS.add(ingredientDTO);
        }
        return ingredientDTOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteIngredientById(Long ingredientId) {
        try {
            ingredientItemRepository.deleteById(ingredientId);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while deleting ingredient with ID: " + ingredientId, e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteIngredientCategoryById(Long ingredientCategoryId) {
        try {
            ingredientCategoryRepository.deleteById(ingredientCategoryId);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while deleting ingredient category with ID: " + ingredientCategoryId, e);
        }
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
