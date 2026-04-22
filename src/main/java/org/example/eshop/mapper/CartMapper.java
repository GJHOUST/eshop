package org.example.eshop.mapper;

import org.example.eshop.dto.CartItemDto;
import org.example.eshop.dto.CreateCartDto;
import org.example.eshop.dto.ViewCartDto;
import org.example.eshop.model.entities.Cart;
import org.example.eshop.model.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "items", source = "items")
    ViewCartDto toViewCartDto(Cart cart);

    @Mapping(target = "productId", source = "product.id")
    CartItemDto toCartItemDto(CartItem item);


}
