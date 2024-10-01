package com.edu.backend.service;

import com.edu.backend.entity.Client;
import com.edu.backend.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;

    public void saveClient(Client client) {
        repository.save(client);
    }

    public Client getClient(long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void deleteClient(Long id) {
        repository.deleteById(id);
    }

    public List<Client> findClientsByNameAndSurname(String name, String surname) {
        return repository.findClientsByNameAndSurname(name, surname);
    }

    public List<Client> getAllClientsPageable(Integer limit, Integer offset) {
        Pageable pageable = null;

        if (limit != null && offset != null) {
            pageable = PageRequest.of(offset / limit, limit); // Создаем объект Pageable
        }

        // Если pageable не задан, возвращаем весь список
        return (pageable != null) ? repository.findAll(pageable).getContent() : repository.findAll();
    }
}