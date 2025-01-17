package com.edu.shopservice.dto;

import com.edu.shopservice.enums.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@Schema(description = "Пользователь")
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @Schema(description = "Имя. Размер от 1 до 100 символов")
    @NotNull
    @Size(min = 1, max = 100)
    private String surname;

    @NotNull
    private LocalDate birthday;

    @NotNull
    private Gender gender;

//    @JsonProperty("registration_date")
    private LocalDate registrationDate;

    @NotNull
//    @JsonProperty("address_id")
    private Long addressId;
}
