package com.phat.food_delivering.repository;

import com.phat.food_delivering.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address save(Address address);
}
