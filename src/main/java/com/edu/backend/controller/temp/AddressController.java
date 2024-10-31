package com.edu.backend.controller.temp;

import com.edu.backend.entity.Address;
import com.edu.backend.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressRepository repository;

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Address findByCountryAndCityAndStreet(
            @RequestParam String country,
            @RequestParam String city,
            @RequestParam String street) {
        return repository.findByCountryAndCityAndStreet(country, city, street).orElseThrow();
    }
}
