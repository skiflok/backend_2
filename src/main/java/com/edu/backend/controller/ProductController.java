package com.edu.backend.controller;

import com.edu.backend.dto.ProductDto;
import com.edu.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ProductController", description = "Контроллер для работы с товарами")
@RestController()
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/v1/product",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ProductController {

    private final ProductService service;

    @PostMapping(
            value = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Добавление товара",
            description = "Добавление поставщика на вход подается json"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(
            @RequestBody @Valid ProductDto productDto) {
        service.addProduct(productDto);
    }

    @PatchMapping(value = "/decrease-product")
    @Operation(
            summary = "Уменьшение количества товара",
            description = "Уменьшение количества товара (на вход запросу подается id товара и на сколько уменьшить)"
    )
    public void decreaseProduct(
            @RequestParam Long productId,
            @RequestParam Integer decreaseStockValue) {
        service.decreaseProduct(productId, decreaseStockValue);
    }

    @GetMapping(value = "/{id}")
    @Operation(
            summary = "Получение товара по id",
            description = "Получение товара по id"
    )
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @GetMapping(value = "/all")
    @Operation(
            summary = "Получение всех доступных товаров",
            description = "Получение всех доступных товаров"
    )
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getAllProduct() {
        return service.getAllProduct();
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
            summary = "Удаление товара по id",
            description = "Удаление товара по id"
    )
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        service.deleteById(id);
    }

}
