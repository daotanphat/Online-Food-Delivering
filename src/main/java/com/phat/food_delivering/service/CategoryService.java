package com.phat.food_delivering.service;

import com.phat.food_delivering.model.Category;
import com.phat.food_delivering.request.CreateCategoryRequest;

import java.util.List;

public interface CategoryService {
    public Category createCategory(CreateCategoryRequest request, Long userId);

    public Category getCategoryById(Long id);

    public List<Category> getCategoryByRestaurantId(Long restaurantId);

    public List<Category> getCategoryBySearch(String searchKey);
}
