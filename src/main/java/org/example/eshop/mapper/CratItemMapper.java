package org.example.eshop.mapper;

import org.example.eshop.dto.ViewCartItemDto;
import org.example.eshop.model.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {ProductMapper.class}
)
public interface CratItemMapper {
    ViewCartItemDto toViewCartItemDto(CartItem item);
}
