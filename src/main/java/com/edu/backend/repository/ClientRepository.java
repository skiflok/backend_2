package com.edu.backend.repository;

import com.edu.backend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findClientsByNameAndSurname(String name, String surname);
}