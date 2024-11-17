package com.edu.backend.service;

import com.edu.backend.dto.ProductDto;
import com.edu.backend.entity.Image;
import com.edu.backend.entity.Product;
import com.edu.backend.entity.Supplier;
import com.edu.backend.repository.ImageRepository;
import com.edu.backend.repository.ProductRepository;
import com.edu.backend.repository.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper defaultModelMapper;

    public void addProduct(ProductDto productDto) {
        Supplier supplier = supplierRepository.findById(productDto.getSupplierId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("Поставщик с [id=%d] не найден", productDto.getSupplierId()))
        );
        Image image = imageRepository.findById(productDto.getImageId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("изображение с [id=%s] не найдено", productDto.getImageId()))
        );
        Product product = defaultModelMapper.map(productDto, Product.class);
        product.setImage(image);
        product.setSupplier(supplier);
        product.setLastUpdateDate(LocalDate.now());
        productRepository.save(product);
        log.info("addProduct [{}]", product);
    }

    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> defaultModelMapper.map(product, ProductDto.class))
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Продукт с [id=%d] не найден", id))
                );
    }

    public void decreaseProduct(Long productId, Integer decreaseStockValue) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Продукт с [id=%d] не найден", productId))
        );
        if (decreaseStockValue > product.getAvailableStock()) {
            throw new IllegalArgumentException("Количество товара меньше чем decreaseStockValue");
        }
        product.setAvailableStock(product.getAvailableStock() - decreaseStockValue);
        productRepository.save(product);
        log.info("product update [{}]", product);
    }

    public List<ProductDto> getAllProduct() {
        return productRepository.findAll().stream()
                .map((element) -> defaultModelMapper.map(element, ProductDto.class))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Продукт с [id=%s] не найден", id));
        }
        productRepository.deleteById(id);
    }
}
