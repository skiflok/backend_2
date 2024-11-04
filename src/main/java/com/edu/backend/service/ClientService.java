package com.edu.backend.service;

import com.edu.backend.dto.AddressDto;
import com.edu.backend.dto.ClientDto;
import com.edu.backend.entity.Address;
import com.edu.backend.entity.Client;
import com.edu.backend.repository.AddressRepository;
import com.edu.backend.repository.ClientRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.List;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

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
                    throw new EntityExistsException("Клиент уже существует");
                });


        //todo
//        AddressDto addressDto = clientDto.getAddress();

//        Address address = findAddressOrSaveAndReturnIfNotExists(addressDto);
//
//        log.info("address = {}", address);
//        client.setRegistrationDate(LocalDate.now());
//        client.setAddress(address);
//
//        log.info("client {}", client);
//        client = clientRepository.save(client);
//        log.info("save client id = {}", client.getId());
    }

    public ClientDto getClient(long id) {
        return modelMapper.map(
                clientRepository.findById(id).orElseThrow(EntityNotFoundException::new),
                ClientDto.class
        );
    }

    public void deleteClient(Long id) {
        clientRepository.findById(id)
                .ifPresentOrElse(clientRepository::delete,
                        () -> {
                            throw new EntityNotFoundException(String.format("Клиент с [id=%d] не найден", id));
                        });
    }

    public ClientDto findClientsByNameAndSurname(String name, String surname) {
        return clientRepository.findClientsByNameAndSurname(name, surname)
                .map(client -> modelMapper.map(client, ClientDto.class))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Клиент [name=%s] [surname=%s] не найден",
                        name,
                        surname))
                );
    }

    public Page<ClientDto> getAllClientsPageable(Integer limit, Integer offset) {
        return Stream.of(limit, offset)
                .filter(Objects::nonNull)
                .count() == 2
                ? clientRepository.findAll(PageRequest.of(offset, limit))
                .map(client -> modelMapper.map(client, ClientDto.class))
                : new PageImpl<>(clientRepository.findAll().stream()
                .map(client -> modelMapper.map(client, ClientDto.class))
                .toList());
    }

    public void changeAddress(Long id, AddressDto addressDto) {
        Client client = clientRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("Клиент с [id=%d] не найден", id))
        );
        Address address = findAddressOrSaveAndReturnIfNotExists(addressDto);
        client.setAddress(address);
        clientRepository.save(client);
    }

    private Address findAddressOrSaveAndReturnIfNotExists(AddressDto addressDto) {
        addressDto.setId(null);
        return addressRepository.findByCountryAndCityAndStreet(
                        addressDto.getCountry(),
                        addressDto.getCity(),
                        addressDto.getStreet())
                .orElseGet(() -> addressRepository.save(modelMapper.map(addressDto, Address.class)));
    }
}
