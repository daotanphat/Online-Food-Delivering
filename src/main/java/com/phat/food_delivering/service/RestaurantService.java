package com.phat.food_delivering.service;

import com.phat.food_delivering.dto.RestaurantDTO;
import com.phat.food_delivering.model.Restaurant;
import com.phat.food_delivering.model.User;
import com.phat.food_delivering.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    public Restaurant updateRestaurant(CreateRestaurantRequest req, Long id);

    public void deleteRestaurant(Long id);

    public List<Restaurant> getAllRestaurants();

    public List<Restaurant> searchRestaurant(String key);

    public Restaurant findRestaurantById(Long id);

    public Restaurant findRestaurantByUserId(Long userId);

    public RestaurantDTO addToFavorite(Long restaurantId, User user);

    public Restaurant updateRestaurantStatus(Long restaurantId);
}
