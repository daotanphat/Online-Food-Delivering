package com.phat.food_delivering.service;

import com.phat.food_delivering.dto.CategoryDTO;
import com.phat.food_delivering.model.Category;
import com.phat.food_delivering.request.CreateCategoryRequest;

import java.util.List;

public interface CategoryService {
    public CategoryDTO createCategory(CreateCategoryRequest request, String token);

    public Category getCategoryById(Long id);

    public List<CategoryDTO> getCategoryByRestaurantId(Long restaurantId);

    public List<CategoryDTO> getCategoryBySearch(String searchKey);

    public void deleteCategoryById(Long categoryId);

    public CategoryDTO updateCategory(CreateCategoryRequest request, Long id);
}
