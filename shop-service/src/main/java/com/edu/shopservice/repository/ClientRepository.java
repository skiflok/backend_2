package com.edu.shopservice.repository;

import com.edu.shopservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findClientsByNameAndSurname(String name, String surname);
}