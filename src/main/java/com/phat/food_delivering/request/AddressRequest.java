package com.phat.food_delivering.request;

import lombok.Data;

@Data
public class AddressRequest {
    private String streetAddress;
    private String city;
    private String stateProvince;
    private Long postalCode;
    private String country;
}
