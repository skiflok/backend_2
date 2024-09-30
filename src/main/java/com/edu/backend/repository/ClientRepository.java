package com.edu.backend.repository;

import com.edu.backend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findClientsByNameAndSurname(String name, String surname);
}