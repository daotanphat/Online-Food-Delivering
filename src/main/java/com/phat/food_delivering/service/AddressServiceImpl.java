package com.phat.food_delivering.service;

import com.phat.food_delivering.model.Address;
import com.phat.food_delivering.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public boolean checkDuplicateAddress(Address address, List<Address> addresses) {
        for (Address address1 : addresses) {
            if (address1.getStreetAddress().equalsIgnoreCase(address.getStreetAddress())
                    && address1.getCity().equalsIgnoreCase(address.getCity())
                    && address1.getStateProvince().equalsIgnoreCase(address.getStateProvince())
                    && address1.getPostalCode().equals(address.getPostalCode())
                    && address1.getCountry().equalsIgnoreCase(address.getCountry())
            ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Address findAddress(Address address) {
        return addressRepository.findByStreetAddressAndCityAndStateProvinceAndPostalCodeAndCountry(
                address.getStreetAddress(),
                address.getCity(),
                address.getStateProvince(),
                address.getPostalCode(),
                address.getCountry()
        );
    }
}
