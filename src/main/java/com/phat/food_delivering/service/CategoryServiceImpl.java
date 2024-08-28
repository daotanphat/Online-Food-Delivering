package com.phat.food_delivering.service;

import com.phat.food_delivering.dto.CategoryDTO;
import com.phat.food_delivering.dto.Mapper.CategoryDTOMapper;
import com.phat.food_delivering.dto.RestaurantDTOO;
import com.phat.food_delivering.exception.EntityNotFoundException;
import com.phat.food_delivering.exception.ListEntityNotFoundException;
import com.phat.food_delivering.model.Category;
import com.phat.food_delivering.model.Restaurant;
import com.phat.food_delivering.model.User;
import com.phat.food_delivering.repository.CategoryRepository;
import com.phat.food_delivering.request.CreateCategoryRequest;
import com.phat.food_delivering.security.SecurityConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    RestaurantService restaurantService;
    UserService userService;
    CategoryDTOMapper categoryDTOMapper;

    @Override
    public CategoryDTO createCategory(CreateCategoryRequest request, String token) {
        token = token.replace(SecurityConstants.BEARER, "");
        User user = userService.findUserBasedOnToken(token);

        Category category = new Category();
        RestaurantDTOO restaurantDTOO = restaurantService.findRestaurantByUserId(user.getId());
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantDTOO.id());
        category = categoryDTOMapper.toCategory(request);
        category.setRestaurant(restaurant);
        return categoryDTOMapper.apply(categoryRepository.save(category));
    }

    @Override
    public Category getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return unwrapCategory(category, id);
    }

    @Override
    public List<CategoryDTO> getCategoryByRestaurantId(Long restaurantId) {
        List<CategoryDTO> categories = categoryRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(categoryDTOMapper)
                .collect(Collectors.toList());
        if (categories.isEmpty()) throw new ListEntityNotFoundException("Not found any food's category");
        return categories;
    }

    @Override
    public List<CategoryDTO> getCategoryBySearch(String searchKey) {
        List<CategoryDTO> categories = categoryRepository.getCategoriesBySearch(searchKey)
                .stream()
                .map(categoryDTOMapper)
                .collect(Collectors.toList());
        if (categories.isEmpty()) throw new ListEntityNotFoundException("Not found any food's category");
        return categories;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategoryById(Long categoryId) {
        try {
            categoryRepository.deleteById(categoryId);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while deleting category with ID: " + categoryId, e);
        }
    }

    @Override
    public CategoryDTO updateCategory(CreateCategoryRequest request, Long id) {
        Category category = getCategoryById(id);
        if (!request.getName().equals(category.getName())) {
            category.setName(request.getName());
        }
        if (!request.getDescription().equals(category.getDescription())) {
            category.setDescription(request.getDescription());
        }
        return categoryDTOMapper.apply(categoryRepository.save(category));
    }

    static Category unwrapCategory(Optional<Category> entity, Long id) {
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new EntityNotFoundException(Category.class, id);
        }
    }
}
