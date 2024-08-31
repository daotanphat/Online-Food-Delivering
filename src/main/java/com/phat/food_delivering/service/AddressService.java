package com.phat.food_delivering.service;

import com.phat.food_delivering.dto.AddressDTO;
import com.phat.food_delivering.model.Address;
import com.phat.food_delivering.request.AddressRequest;

import java.util.List;

public interface AddressService {
    public Address save(Address address);

    public List<Address> getAddresses();

    public boolean checkDuplicateAddress(AddressDTO address, List<Address> addresses);

    public AddressDTO findAddress(Address address);

    Address getAddressById(Long id);
}
