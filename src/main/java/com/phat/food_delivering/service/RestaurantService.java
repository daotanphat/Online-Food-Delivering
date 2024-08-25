package com.phat.food_delivering.service;

import com.phat.food_delivering.dto.RestaurantDTO;
import com.phat.food_delivering.dto.RestaurantDTOO;
import com.phat.food_delivering.model.Restaurant;
import com.phat.food_delivering.model.User;
import com.phat.food_delivering.request.AddressRequest;
import com.phat.food_delivering.request.RestaurantRequest;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(RestaurantRequest restaurantRequest, String token);

    public Restaurant updateRestaurant(RestaurantRequest req, Long id);

    public void deleteRestaurant(Long id);

    public List<RestaurantDTOO> getAllRestaurants();

    public List<RestaurantDTOO> searchRestaurant(String key);

    public Restaurant findRestaurantById(Long id);

    public RestaurantDTOO findRestaurantByUserId(Long userId);

    public RestaurantDTO addToFavorite(Long restaurantId, User user);

    public RestaurantDTOO updateRestaurantStatus(Long restaurantId);

    public Restaurant findRestaurantByToken(String token);
}
