package com.edu.backend.controller;

import com.edu.backend.dto.ClientDto;
import com.edu.backend.entity.Client;
import com.edu.backend.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/v1/client")
public class ClientController {

    private final ClientService service;

    //todo delete this. from test
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Client getClient(@PathVariable Long id) {
        return service.getClient(id);
    }

    @PostMapping(value = "/add", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveClient(@Valid @RequestBody ClientDto clientDto) {
        service.saveClient(clientDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClient(@PathVariable Long id) {
        service.deleteClient(id);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Client getClientsByNameAndSurname(
            @RequestParam String name,
            @RequestParam String surname) {
        return service.findClientsByNameAndSurname(name, surname);
    }

    @GetMapping("/get-all")
    @ResponseStatus(HttpStatus.OK)
    public List<Client> getAllClientsPageable(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset) {
        return service.getAllClientsPageable(limit, offset);
    }
}
