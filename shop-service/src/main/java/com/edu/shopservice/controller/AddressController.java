package com.edu.shopservice.controller;

import com.edu.shopservice.entity.Address;
import com.edu.shopservice.repository.AddressRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "AddressController", description = "Контроллер для работы с адресами")
@RestController
@RequestMapping(
        value = "/api/v1/address",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class AddressController {

    private final AddressRepository repository;

    @GetMapping("/search")
    @Operation(
            summary = "Поиск адреса",
            description = "Поиск адреса"
    )
    @ResponseStatus(HttpStatus.OK)
    public Address findByCountryAndCityAndStreet(
            @RequestParam String country,
            @RequestParam String city,
            @RequestParam String street) {
        return repository.findByCountryAndCityAndStreet(country, city, street).orElseThrow();
    }
}
