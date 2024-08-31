package com.phat.food_delivering.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO implements Serializable {
    private Long id;
    private String streetAddress;
    private String city;
    private String stateProvince;
    private Long postalCode;
    private String country;
}
