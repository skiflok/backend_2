package com.edu.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierDto {

    private Long id;

    private String name;

    private AddressDto address;

    private String phoneNumber;
}
