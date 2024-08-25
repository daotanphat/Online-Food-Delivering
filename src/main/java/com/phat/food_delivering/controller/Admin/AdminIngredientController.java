package com.phat.food_delivering.controller.Admin;

import com.phat.food_delivering.dto.IngredientCategoryDTO;
import com.phat.food_delivering.dto.IngredientDTO;
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
    public ResponseEntity<IngredientCategoryDTO> createIngredientCategory(
            @RequestBody CreateIngredientCategoryRequest request,
            @RequestHeader("Authorization") String token
    ) {
        IngredientCategoryDTO ingredientCategoryDTO = ingredientService.createIngredientCategory(request, token);
        return new ResponseEntity<>(ingredientCategoryDTO, HttpStatus.CREATED);
    }

    @PostMapping("/item")
    public ResponseEntity<IngredientDTO> createIngredientItem(
            @RequestBody CreateIngredientItemRequest request,
            @RequestHeader("Authorization") String token) {
        IngredientDTO ingredientDTO = ingredientService.createIngredientItem(request, token);
        return new ResponseEntity<>(ingredientDTO, HttpStatus.CREATED);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<IngredientDTO> updateIngredientItemStatus(@PathVariable Long id) {
        IngredientDTO ingredientDTO = ingredientService.updateIngredientItemStock(id);
        return new ResponseEntity<>(ingredientDTO, HttpStatus.OK);
    }
}
