package com.edu.backend.controller;

import com.edu.backend.dto.AddressDto;
import com.edu.backend.dto.ClientDto;
import com.edu.backend.entity.Client;
import com.edu.backend.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/v1/client")
public class ClientController {

    private final ClientService service;

    //todo delete this. from test
    @GetMapping(
            value = "/{id}",
            produces = "application/json"
    )
    @ResponseStatus(HttpStatus.OK)
    public ClientDto getClient(@PathVariable Long id) {
        return service.getClient(id);
    }

    @PostMapping(
            value = "/add",
            consumes = "application/json",
            produces = "application/json"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public void saveClient(@Valid @RequestBody ClientDto clientDto) {
        service.saveClient(clientDto);
    }

    @DeleteMapping(
            value = "/{id}",
            produces = "application/json"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Long id) {
        service.deleteClient(id);
    }

    @GetMapping(
            value = "/search",
            produces = "application/json"
    )
    @ResponseStatus(HttpStatus.OK)
    public ClientDto getClientsByNameAndSurname(
            @RequestParam String name,
            @RequestParam String surname) {
        return service.findClientsByNameAndSurname(name, surname);
    }

    @GetMapping(
            value = "/all/pageable",
            produces = "application/json"
    )
    @ResponseStatus(HttpStatus.OK)
    public Page<ClientDto> getAllClientsPageable(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset) {
        return service.getAllClientsPageable(limit, offset);
    }

    @PatchMapping(
            value = "/{id}/change-address",
            consumes = "application/json",
            produces = "application/json"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeAddress(
            @PathVariable Long id,
            @RequestBody @Valid AddressDto addressDto) {
        service.changeAddress(id, addressDto);
    }
}
