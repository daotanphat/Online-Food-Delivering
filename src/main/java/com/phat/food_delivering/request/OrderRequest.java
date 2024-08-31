package com.phat.food_delivering.request;

import com.phat.food_delivering.dto.AddressDTO;
import com.phat.food_delivering.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private Long restaurantId;
    private AddressDTO shipAddress;
}
