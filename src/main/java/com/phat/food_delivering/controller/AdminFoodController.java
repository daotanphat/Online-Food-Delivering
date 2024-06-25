package com.phat.food_delivering.controller;

import com.phat.food_delivering.model.Category;
import com.phat.food_delivering.model.Food;
import com.phat.food_delivering.model.Restaurant;
import com.phat.food_delivering.request.CreateFoodRequest;
import com.phat.food_delivering.response.MessageResponse;
import com.phat.food_delivering.service.FoodService;
import com.phat.food_delivering.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {
    @Autowired
    FoodService foodService;
    @Autowired
    RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest request) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());
        Food food = foodService.createFood(request, request.getCategory(), restaurant);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFood(@RequestBody CreateFoodRequest request, @PathVariable Long id) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());
        Food food = foodService.updateFood(request, request.getCategory(), restaurant, id);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id) {
        foodService.deleteFood(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Delete food successfully");
        return new ResponseEntity<>(messageResponse, HttpStatus.NO_CONTENT);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Food> updateFoodStatus(@PathVariable Long id) {
        Food food = foodService.updateFoodStatus(id);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
