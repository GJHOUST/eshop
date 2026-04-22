package org.example.eshop.mapper;

import org.example.eshop.dto.CreateProductDto;
import org.example.eshop.dto.UpdateProductDto;
import org.example.eshop.dto.ViewProductDto;
import org.example.eshop.model.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductMapper {
    ViewProductDto toViewProductDto(Product product);

    @Mapping(target = "id", ignore = true)
    Product toProduct(CreateProductDto dto);

    @Mapping(target = "id", ignore = true)
    void updateProduct(UpdateProductDto dto, @MappingTarget Product product);
}
