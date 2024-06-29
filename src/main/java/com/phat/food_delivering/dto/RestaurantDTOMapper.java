package com.phat.food_delivering.dto;

import com.phat.food_delivering.model.Address;
import com.phat.food_delivering.model.Restaurant;
import com.phat.food_delivering.model.User;
import com.phat.food_delivering.request.RestaurantRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Function;

@Service
public class RestaurantDTOMapper implements Function<Restaurant, RestaurantDTOO> {
    @Override
    public RestaurantDTOO apply(Restaurant restaurant) {
        return new RestaurantDTOO(
                restaurant.getId(),
                restaurant.getOwner(),
                restaurant.getName(),
                restaurant.getDescription(),
                restaurant.getCuisineType(),
                restaurant.getAddress(),
                restaurant.getContactInformation(),
                restaurant.getOpeningHours(),
                restaurant.getImages(),
                restaurant.getRegistrationDate(),
                restaurant.isOpen()
        );
    }

    public Restaurant toRestaurant(RestaurantRequest request, User user, Address address) {
        Restaurant restaurant = new Restaurant();

        restaurant.setName(request.getName());
        restaurant.setDescription(request.getDescription());
        restaurant.setCuisineType(request.getCuisineType());
        restaurant.setAddress(address);
        restaurant.setContactInformation(request.getContactInformation());
        restaurant.setOpeningHours(request.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setImages(request.getImages());
        restaurant.setOwner(user);
        return restaurant;
    }
}
