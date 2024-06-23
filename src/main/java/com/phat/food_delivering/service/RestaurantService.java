package com.phat.food_delivering.service;

import com.phat.food_delivering.dto.RestaurantDTO;
import com.phat.food_delivering.model.Restaurant;
import com.phat.food_delivering.model.User;
import com.phat.food_delivering.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    public Restaurant updateRestaurant(CreateRestaurantRequest req, Long id) throws Exception;

    public void deleteRestaurant(Long id) throws Exception;

    public List<Restaurant> getAllRestaurants();

    public List<Restaurant> searchRestaurant(String key);

    public Restaurant findRestaurantById(Long id) throws Exception;

    public Restaurant findRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDTO addToFavorite(Long restaurantId, User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception;
}
