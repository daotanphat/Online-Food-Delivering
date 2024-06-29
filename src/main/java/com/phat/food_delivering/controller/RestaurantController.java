package com.phat.food_delivering.controller;

import com.phat.food_delivering.dto.RestaurantDTO;
import com.phat.food_delivering.dto.RestaurantDTOMapper;
import com.phat.food_delivering.dto.RestaurantDTOO;
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
public class RestaurantController {
    private final RestaurantDTOMapper restaurantDTOMapper;
    RestaurantService restaurantService;
    UserService userService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService, UserService userService, RestaurantDTOMapper restaurantDTOMapper) {
        this.restaurantService = restaurantService;
        this.userService = userService;
        this.restaurantDTOMapper = restaurantDTOMapper;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDTOO>> getRestaurants() {
        List<RestaurantDTOO> restaurants = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RestaurantDTOO>> findRestaurantBasedOnSearch(@RequestParam String search) {
        List<RestaurantDTOO> restaurants = restaurantService.searchRestaurant(search);
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTOO> findRestaurantBasedOnId(@PathVariable Long id) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(id);
        RestaurantDTOO restaurantDTOO = restaurantDTOMapper.apply(restaurant);
        return new ResponseEntity<>(restaurantDTOO, HttpStatus.OK);
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
