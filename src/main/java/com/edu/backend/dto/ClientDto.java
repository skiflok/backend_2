package com.edu.backend.dto;

import com.edu.backend.enums.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Пользователь")
public class ClientDto {

    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    @Size(min = 1, max = 100)
    private String surname;

    @NotNull
    private LocalDate birthday;

    @NotNull
    private Gender gender;

    @JsonProperty("registration_date")
    private LocalDate registrationDate;

    @NotNull
    private AddressDto address;
}
