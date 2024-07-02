package com.phat.food_delivering.controller;

import com.phat.food_delivering.dto.IngredientCategoryDTO;
import com.phat.food_delivering.dto.IngredientDTO;
import com.phat.food_delivering.dto.Mapper.IngredientCategoryDTOMapper;
import com.phat.food_delivering.dto.Mapper.IngredientDTOMapper;
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
    @Autowired
    private IngredientCategoryDTOMapper ingredientCategoryDTOMapper;
    @Autowired
    private IngredientDTOMapper ingredientDTOMapper;

    @GetMapping("/category/{id}")
    public ResponseEntity<IngredientCategoryDTO> getIngredientCategoryById(@PathVariable Long id) {
        IngredientCategory ingredientCategory = ingredientService.getIngredientCategoryById(id);
        IngredientCategoryDTO ingredientCategoryDTO = ingredientCategoryDTOMapper.apply(ingredientCategory);
        return new ResponseEntity<>(ingredientCategoryDTO, HttpStatus.OK);
    }

    @GetMapping("/category/restaurant/{id}")
    public ResponseEntity<List<IngredientCategoryDTO>> getIngredidentCategoryByRestaurantId(@PathVariable Long id) {
        List<IngredientCategoryDTO> ingredientCategory = ingredientService.getIngredientCategoryByRestaurantId(id);
        return new ResponseEntity<>(ingredientCategory, HttpStatus.OK);
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<IngredientDTO> getIngredientItemById(@PathVariable Long id) {
        IngredientsItem ingredientsItem = ingredientService.getIngredientItemById(id);
        IngredientDTO ingredientDTO = ingredientDTOMapper.apply(ingredientsItem);
        return new ResponseEntity<>(ingredientDTO, HttpStatus.OK);
    }

    @GetMapping("/item/restaurant/{id}")
    public ResponseEntity<List<IngredientDTO>> getIngredientItemByRestaurantId(@PathVariable Long id) {
        List<IngredientDTO> ingredientsItem = ingredientService.getIngredientItemByRestaurantId(id);
        return new ResponseEntity<>(ingredientsItem, HttpStatus.OK);
    }
}
