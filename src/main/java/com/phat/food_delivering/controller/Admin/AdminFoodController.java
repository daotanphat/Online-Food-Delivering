package com.phat.food_delivering.controller.Admin;

import com.phat.food_delivering.dto.FoodDTO;
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
    public ResponseEntity<FoodDTO> createFood(
            @RequestBody CreateFoodRequest request,
            @RequestHeader("Authorization") String token) throws Exception {
        FoodDTO food = foodService.createFood(request, token);
        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodDTO> updateFood(@RequestBody CreateFoodRequest request, @PathVariable Long id) throws Exception {
        FoodDTO food = foodService.updateFood(request, id);
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
    public ResponseEntity<FoodDTO> updateFoodStatus(@PathVariable Long id) {
        FoodDTO food = foodService.updateFoodStatus(id);
        return new ResponseEntity<>(food, HttpStatus.OK);
    }
}
