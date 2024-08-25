package com.phat.food_delivering.request;

import com.phat.food_delivering.model.Address;
import com.phat.food_delivering.model.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantRequest {
    private long id;
    private String name;
    private String description;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;
}
