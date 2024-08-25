package com.phat.food_delivering.dto.Mapper;

import com.phat.food_delivering.dto.CategoryDTO;
import com.phat.food_delivering.model.Category;
import com.phat.food_delivering.request.CreateCategoryRequest;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CategoryDTOMapper implements Function<Category, CategoryDTO> {
    @Override
    public CategoryDTO apply(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public Category toCategory(CreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        return category;
    }
}
