package com.edu.backend.repository;

import com.edu.backend.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByCountryAndCityAndStreet(String country, String city, String street);
}