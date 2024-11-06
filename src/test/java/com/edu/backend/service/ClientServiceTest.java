package com.edu.backend.service;

import com.edu.backend.dto.AddressDto;
import com.edu.backend.dto.ClientDto;
import com.edu.backend.entity.Address;
import com.edu.backend.entity.Client;
import com.edu.backend.enums.Gender;
import com.edu.backend.repository.AddressRepository;
import com.edu.backend.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;


@Slf4j
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
    private Client client;

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

        client = Client.builder()
                .id(1L)
                .name("Name")
                .surname("Surname")
                .birthday(LocalDate.now())
                .gender(Gender.MALE)
                .registrationDate(LocalDate.now())
                .address(modelMapper.map(addressDto, Address.class))
                .build();
    }

    @Test
    public void getClientShouldOk() {
        Mockito.when(clientRepository.findById(any())).thenReturn(Optional.of(client));

        ClientDto clientDtoTest = clientService.getClient(1L);

        log.info("clientDto = [{}]", clientDto);
        log.info("clientDtoTest = [{}]", clientDtoTest);

        assertEquals(clientDtoTest, clientDto);
    }

    @Test
    public void getClientShouldThrowEntityNotFoundExceptionWhenIdNotExist() {
        Mockito.when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> clientService.getClient(1L));

        assertEquals(exception.getMessage(), "Клиент с [id=1] не найден");
    }

}