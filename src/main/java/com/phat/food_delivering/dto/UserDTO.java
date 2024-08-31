package com.phat.food_delivering.dto;

import com.phat.food_delivering.model.Address;
import com.phat.food_delivering.model.USER_ROLE;

import java.util.List;

public record UserDTO(
        String fullName,
        String email,
        USER_ROLE role,
        AddressDTO address,
        List<AddressDTO> addresses,
        List<RestaurantDTO> favorites
) {
}