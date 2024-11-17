package com.edu.backend.service;

import com.edu.backend.dto.AddressDto;
import com.edu.backend.dto.SupplierDto;
import com.edu.backend.entity.Address;
import com.edu.backend.entity.Supplier;
import com.edu.backend.repository.AddressRepository;
import com.edu.backend.repository.SupplierRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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
        Address address = addressRepository.findById(supplierDto.getAddress())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Адресс с [id=%d] не найден", supplierDto.getAddress())));
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
    }

    public List<SupplierDto> getAll() {
        //todo
        return Collections.emptyList();
    }

    public SupplierDto getById(Long id) {
        return null;
    }
}
