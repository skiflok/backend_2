package com.edu.backend.controller;

import com.edu.backend.dto.ProductDto;
import com.edu.backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService service;

    @PostMapping(
            value = "/add",
            consumes = "multipart/form-data"
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
