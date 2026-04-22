package org.example.eshop.controller;

import jakarta.validation.Valid;
import org.example.eshop.dto.CreateProductDto;
import org.example.eshop.dto.UpdateProductDto;
import org.example.eshop.dto.ViewProductDto;
import org.example.eshop.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ViewProductDto create(@Valid @RequestBody CreateProductDto dto) {
        return productService.create(dto);
    }

    @GetMapping("/{id}")
    public ViewProductDto getById(@PathVariable UUID id) {
        return productService.getById(id);
    }

    @GetMapping
    public Page<ViewProductDto> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productService.getAll(pageable);
    }

    @PutMapping("/{id}")
    public ViewProductDto update(@PathVariable UUID id, @Valid @RequestBody UpdateProductDto dto) {
        return productService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        productService.delete(id);
    }
}
