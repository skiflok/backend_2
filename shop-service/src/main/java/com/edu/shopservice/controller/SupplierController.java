package com.edu.shopservice.controller;

import com.edu.shopservice.dto.AddressDto;
import com.edu.shopservice.dto.SupplierDto;
import com.edu.shopservice.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "SupplierController", description = "Контроллер для работы с поставщиками")
@RestController
@RequestMapping("/api/v1/supplier")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService service;

    @Operation(
            summary = "Добавление поставщика",
            description = "Добавление поставщика (на вход подается json, соответствующей структуре, описанной сверху)."
    )
    @PostMapping(
            value = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public void addSupplier(@Valid @RequestBody SupplierDto supplierDto) {
        service.add(supplierDto);
    }

    @Operation(
            summary = "Изменение адреса поставщика",
            description = "Изменение адреса поставщика (параметры: Id и новый адрес в виде json в соответствии с выше описанным форматом)"
    )
    @PatchMapping(
            value = "/change-address",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void changeSupplierAddress(@RequestParam Long supplierId,
                                      @Valid @RequestBody AddressDto addressDto) {
        service.changeAddress(supplierId, addressDto);
    }

    @Operation(
            summary = "Удаление поставщика",
            description = "Удаление поставщика по id"
    )
    @DeleteMapping(
            value = "/{id}"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        service.deleteById(id);
    }

    @Operation(
            summary = "Получение всех поставщиков",
            description = "Получение всех поставщиков"
    )
    @GetMapping(
            value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public List<SupplierDto> getAll() {
        return service.getAll();
    }

    @Operation(
            summary = "Получение поставщика",
            description = "Получение поставщика по id"
    )
    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.OK)
    public SupplierDto getById(@PathVariable Long id) {
        return service.getById(id);
    }
}
