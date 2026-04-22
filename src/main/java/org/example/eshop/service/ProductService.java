package org.example.eshop.service;

import org.example.eshop.dto.CreateProductDto;
import org.example.eshop.dto.UpdateProductDto;
import org.example.eshop.dto.ViewProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {
    ViewProductDto create(CreateProductDto createProductDto);

    ViewProductDto getById(UUID id);

    Page<ViewProductDto> getAll(Pageable pageable);

    ViewProductDto update(UUID id, UpdateProductDto updateProductDto);

    void delete(UUID id);
}
