package com.phat.food_delivering.controller;

import com.phat.food_delivering.model.Food;
import com.phat.food_delivering.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/food")
public class FoodController {
    @Autowired
    FoodService foodService;

    @GetMapping("/{id}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long id) {
        Food food = foodService.getFoodById(id);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<Food>> getFoodByRestaurantId(@PathVariable Long id) {
        List<Food> foods = foodService.getFoodByRestaurantId(id);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/restaurant-filter/{id}")
    public ResponseEntity<List<Food>> getFoodIsvegeterianOrSeasonalAndCategoryIdBasedOnRestauarntId(
            @PathVariable Long id,
            @RequestParam boolean isVegetarian,
            @RequestParam boolean isSeasonal,
            @RequestParam String categoryName
    ) {
        List<Food> foods = foodService.getFoodIsvegeterianOrSeasonalAndCategoryIdBasedOnRestauarntId(id, isVegetarian, isSeasonal, categoryName);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Food>> getFoodBasedOnSearch(@RequestParam String search) {
        List<Food> foods = foodService.getFoodBasedOnKeyWord(search);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
