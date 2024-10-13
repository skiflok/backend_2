package com.edu.backend.service;

import com.edu.backend.dto.AddressDto;
import com.edu.backend.dto.ClientDto;
import com.edu.backend.entity.Address;
import com.edu.backend.entity.Client;
import com.edu.backend.repository.AddressRepository;
import com.edu.backend.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    public void saveClient(ClientDto clientDto) {
        log.info("client dto {}", clientDto);
        Client client = modelMapper.map(clientDto, Client.class);

        clientRepository.findClientsByNameAndSurname(client.getName(), client.getSurname())
                .ifPresent(find -> {
                    throw new EntityNotFoundException("Клиент уже существует");
                });

        AddressDto addressDto = clientDto.getAddress();

        Address address = addressRepository.findByCountryAndCityAndStreet
                        (
                                addressDto.getCountry(),
                                addressDto.getCity(),
                                addressDto.getStreet()
                        )
                .orElseGet(() -> addressRepository.save(modelMapper.map(addressDto, Address.class)));

        log.info("address = {}", address);
        client.setRegistrationDate(LocalDate.now());
        client.setAddress(address);

        log.info("client {}", client);
        clientRepository.save(client);
        log.info("save client id = {}", client.getId());
    }

    public Client getClient(long id) {
        return clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    public Client findClientsByNameAndSurname(String name, String surname) {
        return clientRepository.findClientsByNameAndSurname(name, surname)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));
    }

    public List<Client> getAllClientsPageable(Integer limit, Integer offset) {
        Pageable pageable = null;

        if (limit != null && offset != null) {
            pageable = PageRequest.of(offset / limit, limit); // Создаем объект Pageable
        }

        // Если pageable не задан, возвращаем весь список
        return (pageable != null) ? clientRepository.findAll(pageable).getContent() : clientRepository.findAll();
    }
}