package com.edu.shopservice.service;

import com.edu.shopservice.dto.AddressDto;
import com.edu.shopservice.dto.SupplierDto;
import com.edu.shopservice.entity.Address;
import com.edu.shopservice.entity.Supplier;
import com.edu.shopservice.repository.AddressRepository;
import com.edu.shopservice.repository.SupplierRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final AddressRepository addressRepository;
    private final ModelMapper defaultModelMapper;

    public void add(SupplierDto supplierDto) {
        supplierRepository.findById(supplierDto.getId()).ifPresent(s -> {
            throw new EntityExistsException(String.format("Поставщик с [id=%d] уже существует", supplierDto.getId()));
        });
        Address address = addressRepository.findById(supplierDto.getAddressId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Адресс с [id=%d] не найден", supplierDto.getAddressId())));
        Supplier supplier = defaultModelMapper.map(supplierDto, Supplier.class);
        supplier.setAddress(address);
        supplier = supplierRepository.save(supplier);

        log.info("Supplier saved: {}", supplier);
    }

    public void changeAddress(Long supplierId, AddressDto addressDto) {
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Поставщик с [id=%d] не существует", supplierId))
        );
        addressRepository.findByCountryAndCityAndStreet(
                        addressDto.getCountry(),
                        addressDto.getCity(),
                        addressDto.getStreet())
                .ifPresent(address -> {
                    throw new EntityExistsException("Адрес с данными параметнами уже существует");
                });
        addressDto.setId(null);
        Address address = addressRepository.save(defaultModelMapper.map(addressDto, Address.class));
        supplier.setAddress(address);
        supplier = supplierRepository.save(supplier);
        log.info("Supplier update [{}]", supplier);
    }

    public void deleteById(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Поставщик с [id=%s] не найден", id));
        }
        supplierRepository.deleteById(id);
        log.info("Supplier [id = {}] delete", id);
    }

    public List<SupplierDto> getAll() {
        return supplierRepository.findAll().stream()
                .map((element) -> defaultModelMapper.map(element, SupplierDto.class))
                .collect(Collectors.toList());
    }

    public SupplierDto getById(Long id) {
        return defaultModelMapper.map(
                supplierRepository.findById(id)
                        .orElseThrow(() ->
                                new EntityNotFoundException(String.format("Поставщик с [id=%d] не найден", id))
                        ),
                SupplierDto.class
        );
    }
}
