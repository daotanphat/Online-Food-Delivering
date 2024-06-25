package com.phat.food_delivering.controller;

import com.phat.food_delivering.model.Category;
import com.phat.food_delivering.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CustomerCategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Category>> getCategoryByRestaurantId(@PathVariable Long restaurantId) {
        List<Category> categories = categoryService.getCategoryByRestaurantId(restaurantId);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Category>> getCategoryBySearch(@RequestParam String search) {
        List<Category> categories = categoryService.getCategoryBySearch(search);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
