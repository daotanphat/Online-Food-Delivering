package com.phat.food_delivering.dto;

import com.phat.food_delivering.model.Address;

import java.util.Date;
import java.util.List;

public record OrderDTO(
        Long id,
        UserDTO customer,
        RestaurantDTOO restaurant,
        Long totalAmount,
        String status,
        Date createAt,
        AddressDTO deliveryAddress,
        List<OrderItemDTO> items,
        int totalItem,
        Long totalPrice
) {
}
