package com.edu.backend.repository;

import com.edu.backend.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByCountryAndCityAndStreet(String country, String city, String street);
}