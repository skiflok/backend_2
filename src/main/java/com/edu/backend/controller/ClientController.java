package com.edu.backend.controller;

import com.edu.backend.entity.Client;
import com.edu.backend.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientService service;

    @GetMapping("/{clientId}")
    public Client getClient (@PathVariable Long clientId) {
        return service.getClient(clientId);
    }
}
