package com.phat.food_delivering.controller;

import com.phat.food_delivering.dto.RestaurantDTO;
import com.phat.food_delivering.model.Restaurant;
import com.phat.food_delivering.model.User;
import com.phat.food_delivering.security.SecurityConstants;
import com.phat.food_delivering.service.RestaurantService;
import com.phat.food_delivering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/restaurant")
public class CustomerRestaurantController {
    RestaurantService restaurantService;
    UserService userService;

    @Autowired
    public CustomerRestaurantController(RestaurantService restaurantService, UserService userService) {
        this.restaurantService = restaurantService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> findRestaurantBasedOnSearch(@RequestParam String search) {
        List<Restaurant> restaurants = restaurantService.searchRestaurant(search);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantBasedOnId(@PathVariable Long id) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/add-favorites/{id}")
    public ResponseEntity<RestaurantDTO> addFavoriteRestaurant(
            @RequestHeader("Authorization") String token,
            @PathVariable Long id
    ) throws Exception {
        token = token.replace(SecurityConstants.BEARER, "");
        User user = userService.findUserBasedOnToken(token);
        RestaurantDTO restaurantDTO = restaurantService.addToFavorite(id, user);
        return new ResponseEntity<>(restaurantDTO, HttpStatus.OK);
    }
}
