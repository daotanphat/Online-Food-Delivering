package com.phat.food_delivering.service;

import com.phat.food_delivering.exception.EntityNotFoundException;
import com.phat.food_delivering.model.Category;
import com.phat.food_delivering.model.Restaurant;
import com.phat.food_delivering.repository.CategoryRepository;
import com.phat.food_delivering.request.CreateCategoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    RestaurantService restaurantService;

    @Override
    public Category createCategory(CreateCategoryRequest request, Long userId) {
        Category category = new Category();
        Restaurant restaurant = restaurantService.findRestaurantByUserId(userId);
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setRestaurant(restaurant);
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return unwrapCategory(category, id);
    }

    @Override
    public List<Category> getCategoryByRestaurantId(Long restaurantId) {
        List<Category> categories = categoryRepository.findByRestaurantId(restaurantId);
        return categories;
    }

    @Override
    public List<Category> getCategoryBySearch(String searchKey) {
        List<Category> categories = categoryRepository.getCategoriesBySearch(searchKey);
        return categories;
    }

    static Category unwrapCategory(Optional<Category> entity, Long id) {
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new EntityNotFoundException(Category.class, id);
        }
    }
}
