package com.phat.food_delivering.service;

import com.phat.food_delivering.model.Address;

import java.util.List;

public interface AddressService {
    public Address save(Address address);

    public List<Address> getAddresses();

    public boolean checkDuplicateAddress(Address address, List<Address> addresses);

    public Address findAddress(Address address);
}
