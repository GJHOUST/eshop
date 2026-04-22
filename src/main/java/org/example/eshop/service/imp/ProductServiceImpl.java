package org.example.eshop.service.imp;

import jakarta.transaction.Transactional;
import org.example.eshop.exception.NotFoundException;
import org.example.eshop.dto.CreateProductDto;
import org.example.eshop.dto.UpdateProductDto;
import org.example.eshop.dto.ViewProductDto;
import org.example.eshop.mapper.ProductMapper;
import org.example.eshop.model.entities.Product;
import org.example.eshop.model.repositories.ProductRepository;
import org.example.eshop.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ViewProductDto create(CreateProductDto createProductDto) {
        Product product = productMapper.toProduct(createProductDto);
        return productMapper.toViewProductDto(productRepository.save(product));
    }

    @Override
    @Transactional
    public ViewProductDto getById(UUID id) {
        return productRepository.findById(id)
                .map(productMapper::toViewProductDto)
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));
    }

    @Override
    @Transactional
    public Page<ViewProductDto> getAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toViewProductDto);
    }

    @Override
    public ViewProductDto update(UUID id, UpdateProductDto updateProductDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));

        productMapper.updateProduct(updateProductDto, product);
        return productMapper.toViewProductDto(productRepository.save(product));
    }

    @Override
    public void delete(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Product not found: " + id);
        }
        productRepository.deleteById(id);
    }
}