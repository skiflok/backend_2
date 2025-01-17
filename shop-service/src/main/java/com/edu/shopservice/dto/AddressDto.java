package com.edu.shopservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Адрес")
public class AddressDto {

    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String country;

    @NotNull
    @Size(min = 1, max = 100)
    private String city;

    @NotNull
    @Size(min = 1, max = 100)
    private String street;
}
