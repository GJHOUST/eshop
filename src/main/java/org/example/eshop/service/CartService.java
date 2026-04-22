package org.example.eshop.service;

import org.example.eshop.dto.AddCartItemDto;
import org.example.eshop.dto.CreateCartDto;
import org.example.eshop.dto.UpdateCartItemDto;
import org.example.eshop.dto.ViewCartDto;

import java.util.UUID;

public interface CartService {
    ViewCartDto create(CreateCartDto createCartDto);

    ViewCartDto getById(UUID id);

    ViewCartDto getByUserId(UUID userId);

    ViewCartDto addItem(UUID cartId, AddCartItemDto addCartItemDto);

    ViewCartDto updateItem(UUID cartId, UUID itemId, UpdateCartItemDto updateCartItemDto);

    ViewCartDto removeItem(UUID cartId, UUID itemId);

    void delete(UUID cartId);
}
