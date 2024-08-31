package com.phat.food_delivering.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String streetAddress;
    private String city;
    private String stateProvince;
    private Long postalCode;
    private String country;

    @OneToOne(mappedBy = "address")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(streetAddress, address.streetAddress) &&
                Objects.equals(city, address.city) &&
                Objects.equals(stateProvince, address.stateProvince) &&
                Objects.equals(postalCode, address.postalCode) &&
                Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetAddress, city, stateProvince, postalCode, country);
    }
}
