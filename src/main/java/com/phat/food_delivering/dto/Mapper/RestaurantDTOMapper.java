package com.phat.food_delivering.dto.Mapper;

import com.phat.food_delivering.dto.CategoryDTO;
import com.phat.food_delivering.dto.IngredientDTO;
import com.phat.food_delivering.dto.RestaurantDTOO;
import com.phat.food_delivering.model.*;
import com.phat.food_delivering.request.RestaurantRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class RestaurantDTOMapper implements Function<Restaurant, RestaurantDTOO> {
    @Autowired
    CategoryDTOMapper categoryDTOMapper;

    @Override
    public RestaurantDTOO apply(Restaurant restaurant) {
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for (Category category : restaurant.getCategories()) {
            categoryDTOS.add(categoryDTOMapper.apply(category));
        }
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
                restaurant.isOpen(),
                categoryDTOS
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
