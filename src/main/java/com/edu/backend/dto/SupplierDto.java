package com.edu.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Поставщик")
public class SupplierDto {

    private Long id;

    private String name;

    private Long addressId;

    private String phoneNumber;
}
