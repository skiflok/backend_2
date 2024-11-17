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
    }

    public void deleteById(Long supplierId) {
    }

    public List<SupplierDto> getAll() {
        //todo
        return Collections.emptyList();
    }

    public SupplierDto getById(Long id) {
        return null;
    }
}
