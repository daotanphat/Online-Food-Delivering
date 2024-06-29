package com.phat.food_delivering.dto;

import com.phat.food_delivering.model.Address;
import com.phat.food_delivering.request.AddressRequest;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AddressDTOMapper implements Function<Address, AddressDTOMapper> {
    @Override
    public AddressDTOMapper apply(Address address) {
        return null;
    }

    public Address toAddress(AddressRequest request) {
        Address address = new Address();
        address.setStreetAddress(request.getStreetAddress());
        address.setStateProvince(request.getStateProvince());
        address.setCity(request.getCity());
        address.setCountry(request.getCountry());
        address.setPostalCode(request.getPostalCode());
        return address;
    }
}
