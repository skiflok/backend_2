package com.edu.backend.dto;

import com.edu.backend.enums.Gender;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientDto {
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

    @NotNull
    private AddressDto address;
}
