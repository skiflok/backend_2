package com.edu.backend.service;

import com.edu.backend.dto.AddressDto;
import com.edu.backend.dto.ClientDto;
import com.edu.backend.entity.Client;
import com.edu.backend.enums.Gender;
import com.edu.backend.repository.AddressRepository;
import com.edu.backend.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@Disabled
@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private AddressRepository addressRepository;
    @Spy
    private ModelMapper modelMapper;
    @InjectMocks
    private ClientService clientService;

    private ClientDto clientDto;
    private AddressDto addressDto;

    @BeforeEach
    public void init() {
        clientDto = ClientDto.builder()
                .id(1L)
                .name("Name")
                .surname("Surname")
                .birthday(LocalDate.now())
                .gender(Gender.MALE)
                .registrationDate(LocalDate.now())
                .addressId(1L)
                .build();

        addressDto = AddressDto.builder()
                .id(1L)
                .country("country")
                .city("city")
                .street("street")
                .build();

    }

    @Test
    public void getClientShouldOk() {
        Mockito.when(clientRepository.findById(any())).thenReturn(Optional.of(new Client()));

        clientService.getClient(1L);
        System.out.println(clientDto.toString());
    }

}