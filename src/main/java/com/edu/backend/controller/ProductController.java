package com.edu.backend.controller;

import com.edu.backend.dto.ProductDto;
import com.edu.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "ProductController", description = "Контроллер для работы с товарами")
@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService service;

    @PostMapping(
            value = "/add",
            consumes = "multipart/form-data"
    )
    @Operation(
            summary = "Добавление товара",
            description = "Добавление поставщика на вход подается json"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(
            @RequestBody @Valid ProductDto productDto,
            @RequestPart MultipartFile imageFile) {
        service.addProduct(productDto, imageFile);
    }

//    @PostMapping(
//            value = "/add",
//            consumes = "multipart/form-data",
//            produces = "application/json"
//    )
    public void decreaseProduct() {}

    @GetMapping(
            value = "/{id}",
            produces = "application/json"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    public void getAllProduct() {}

    public void deleteProduct() {}

}

/*
*
Добавление товара (на вход подается json, соответствующей структуре, описанной сверху).

Уменьшение количества товара (на вход запросу подается id товара и на сколько уменьшить)

Получение товара по id

Получение всех доступных товаров

Удаление товара по id
* */