package org.example.eshop.controller;

import jakarta.validation.Valid;
import org.example.eshop.dto.AddCartItemDto;
import org.example.eshop.dto.CreateCartDto;
import org.example.eshop.dto.UpdateCartItemDto;
import org.example.eshop.dto.ViewCartDto;
import org.example.eshop.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ViewCartDto create(@Valid @RequestBody CreateCartDto dto) {
        return cartService.create(dto);
    }

    @GetMapping("/{id}")
    public ViewCartDto getById(@PathVariable UUID id) {
        return cartService.getById(id);
    }

    @GetMapping("/by-user/{userId}")
    public ViewCartDto getByUserId(@PathVariable UUID userId) {
        return cartService.getByUserId(userId);
    }

    @PostMapping("/{cartId}/items")
    public ViewCartDto addItem(@PathVariable UUID cartId, @Valid @RequestBody AddCartItemDto dto) {
        return cartService.addItem(cartId, dto);
    }

    @PutMapping("/{cartId}/items/{itemId}")
    public ViewCartDto updateItem(@PathVariable UUID cartId, @PathVariable UUID itemId, @Valid @RequestBody UpdateCartItemDto dto) {
        return cartService.updateItem(cartId, itemId, dto);
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    public ViewCartDto removeItem(@PathVariable UUID cartId, @PathVariable UUID itemId) {
        return cartService.removeItem(cartId, itemId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        cartService.delete(id);
    }
}
