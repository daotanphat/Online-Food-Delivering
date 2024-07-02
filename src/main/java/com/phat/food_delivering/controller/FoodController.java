package com.phat.food_delivering.controller;

import com.phat.food_delivering.dto.FoodDTO;
import com.phat.food_delivering.dto.Mapper.FoodDTOMapper;
import com.phat.food_delivering.exception.ListEntityNotFoundException;
import com.phat.food_delivering.model.Food;
import com.phat.food_delivering.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    @Autowired
    FoodService foodService;

    @Autowired
    FoodDTOMapper foodDTOMapper;

    @GetMapping("/{id}")
    public ResponseEntity<FoodDTO> getFoodById(@PathVariable Long id) {
        Food food = foodService.getFoodById(id);
        FoodDTO foodDTO = foodDTOMapper.apply(food);
        return new ResponseEntity<>(foodDTO, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<FoodDTO>> getFoodByRestaurantId(@PathVariable Long id) {
        List<FoodDTO> foods = foodService.getFoodByRestaurantId(id);
        if (foods.isEmpty()) {
            throw new ListEntityNotFoundException("Not found any food");
        }
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/restaurant-filter/{id}")
    public ResponseEntity<List<FoodDTO>> getFoodIsvegeterianOrSeasonalAndCategoryIdBasedOnRestauarntId(
            @PathVariable Long id,
            @RequestParam boolean isVegetarian,
            @RequestParam boolean isSeasonal,
            @RequestParam String categoryName
    ) {
        List<FoodDTO> foods = foodService.getFoodIsvegeterianOrSeasonalAndCategoryIdBasedOnRestauarntId(id, isVegetarian, isSeasonal, categoryName);
        if (foods.isEmpty()) {
            throw new ListEntityNotFoundException("Not found any food");
        }
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<FoodDTO>> getFoodBasedOnSearch(@RequestParam String search) {
        List<FoodDTO> foods = foodService.getFoodBasedOnKeyWord(search);
        if (foods.isEmpty()) {
            throw new ListEntityNotFoundException("Not found any food");
        }
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }
}
