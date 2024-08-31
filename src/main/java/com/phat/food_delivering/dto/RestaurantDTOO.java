package com.phat.food_delivering.dto;

import com.phat.food_delivering.model.*;

import java.time.LocalDateTime;
import java.util.List;

public record RestaurantDTOO(
        Long id,
        User owner,
        String name,
        String description,
        String cuisineType,
        Address address,
        ContactInformation contactInformation,
        String openingHours,
        List<String> images,
        LocalDateTime registrationDate,
        boolean open,
        List<CategoryDTO> categories
) {
    // Static method to create a no-args RestaurantDTOO
    public static RestaurantDTOO noArgConstructor() {
        return new RestaurantDTOO(
                null, null, "", "", "", null, null, "", List.of(), LocalDateTime.now(), false, List.of()
        );
    }
}
