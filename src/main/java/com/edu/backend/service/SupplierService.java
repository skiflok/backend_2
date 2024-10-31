package com.edu.backend.service;

import com.edu.backend.dto.AddressDto;
import com.edu.backend.dto.SupplierDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class SupplierService {


    public void add(SupplierDto supplierDto) {
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
