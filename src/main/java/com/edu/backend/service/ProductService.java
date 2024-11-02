package com.edu.backend.service;

import com.edu.backend.dto.ProductDto;
import com.edu.backend.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public void addProduct(ProductDto productDto) {
        log.info("addProduct");
    }

    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> modelMapper.map(product, ProductDto.class))
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Продукт с [id=%d] не найден", id))
                );
    }

    public void decreaseProduct(Long productId, Integer decreaseStockValue) {
    }

    public void getAllProduct() {
    }

    public void deleteById(Long id) {
    }
}
