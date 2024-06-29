package com.phat.food_delivering.controller;

import com.phat.food_delivering.dto.RestaurantDTOO;
import com.phat.food_delivering.model.Restaurant;
import com.phat.food_delivering.model.User;
import com.phat.food_delivering.request.AddressRequest;
import com.phat.food_delivering.request.RestaurantRequest;
import com.phat.food_delivering.response.MessageResponse;
import com.phat.food_delivering.security.SecurityConstants;
import com.phat.food_delivering.service.RestaurantService;
import com.phat.food_delivering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @Autowired
    UserService userService;

    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody RestaurantRequest restaurantRequest,
            @RequestHeader("Authorization") String token
    ) {
        Restaurant restaurant = restaurantService.createRestaurant(restaurantRequest, token);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestBody RestaurantRequest req,
            @PathVariable Long id
    ) throws Exception {
        Restaurant restaurant = restaurantService.updateRestaurant(req, id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
            @PathVariable Long id
    ) throws Exception {
        restaurantService.deleteRestaurant(id);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Restaurant deleted");
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<RestaurantDTOO> updateRestaurantStatus(@PathVariable Long id) throws Exception {
        RestaurantDTOO restaurantDTOO = restaurantService.updateRestaurantStatus(id);
        return new ResponseEntity<>(restaurantDTOO, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<RestaurantDTOO> findRestaurantByUserId(@RequestHeader("Authorization") String token) throws Exception {
        token = token.replace(SecurityConstants.BEARER, "");
        User user = userService.findUserBasedOnToken(token);
        RestaurantDTOO restaurantDTOO = restaurantService.findRestaurantByUserId(user.getId());
        return new ResponseEntity<>(restaurantDTOO, HttpStatus.OK);
    }
}
