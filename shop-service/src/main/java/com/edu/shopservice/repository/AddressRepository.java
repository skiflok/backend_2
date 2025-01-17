package com.edu.shopservice.repository;

import com.edu.shopservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByCountryAndCityAndStreet(String country, String city, String street);
}