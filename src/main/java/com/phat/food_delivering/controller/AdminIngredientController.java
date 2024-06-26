package com.phat.food_delivering.controller;

import com.phat.food_delivering.model.IngredientCategory;
import com.phat.food_delivering.model.IngredientsItem;
import com.phat.food_delivering.request.CreateIngredientCategoryRequest;
import com.phat.food_delivering.request.CreateIngredientItemRequest;
import com.phat.food_delivering.service.IngredientService;
import com.phat.food_delivering.service.RestaurantService;
import com.phat.food_delivering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/ingredient")
public class AdminIngredientController {
    @Autowired
    IngredientService ingredientService;
    @Autowired
    UserService userService;
    @Autowired
    RestaurantService restaurantService;

    @PostMapping("/category")
    public ResponseEntity<IngredientCategory> createIngredientCategory(
            @RequestBody CreateIngredientCategoryRequest request
    ) {
        IngredientCategory ingredientCategory = ingredientService.createIngredientCategory(request.getName(), request.getRestaurantId());
        return new ResponseEntity<>(ingredientCategory, HttpStatus.CREATED);
    }

    @PostMapping("/item")
    public ResponseEntity<IngredientsItem> createIngredientItem(@RequestBody CreateIngredientItemRequest request) {
        IngredientsItem ingredientsItem = ingredientService.createIngredientItem(request.getName(), request.getCategoryId(), request.getRestaurantId());
        return new ResponseEntity<>(ingredientsItem, HttpStatus.CREATED);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<IngredientsItem> updateIngredientItemStatus(@PathVariable Long id) {
        IngredientsItem ingredientsItem = ingredientService.updateIngredientItemStock(id);
        return new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
    }
}
