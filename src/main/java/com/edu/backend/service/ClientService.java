package com.edu.backend.service;

import com.edu.backend.entity.Client;
import com.edu.backend.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;

    public Client getClient(long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
