package com.phat.food_delivering.controller;

import com.phat.food_delivering.model.IngredientCategory;
import com.phat.food_delivering.model.IngredientsItem;
import com.phat.food_delivering.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {
    @Autowired
    IngredientService ingredientService;

    @GetMapping("/category/{id}")
    public ResponseEntity<IngredientCategory> getIngredientCategoryById(@PathVariable Long id) {
        IngredientCategory ingredientCategory = ingredientService.getIngredientCategoryById(id);
        return new ResponseEntity<>(ingredientCategory, HttpStatus.OK);
    }

    @GetMapping("/category/restaurant/{id}")
    public ResponseEntity<List<IngredientCategory>> getIngredidentCategoryByRestaurantId(@PathVariable Long id) {
        List<IngredientCategory> ingredientCategory = ingredientService.getIngredientCategoryByRestaurantId(id);
        return new ResponseEntity<>(ingredientCategory, HttpStatus.OK);
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<IngredientsItem> getIngredientItemById(@PathVariable Long id) {
        IngredientsItem ingredientsItem = ingredientService.getIngredientItemById(id);
        return new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
    }

    @GetMapping("/item/restaurant/{id}")
    public ResponseEntity<List<IngredientsItem>> getIngredientItemByRestaurantId(@PathVariable Long id) {
        List<IngredientsItem> ingredientsItem = ingredientService.getIngredientItemByRestaurantId(id);
        return new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
    }
}
