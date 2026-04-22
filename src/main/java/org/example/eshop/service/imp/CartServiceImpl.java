package org.example.eshop.service.imp;

import jakarta.transaction.Transactional;
import org.example.eshop.exception.NotFoundException;
import org.example.eshop.dto.AddCartItemDto;
import org.example.eshop.dto.CreateCartDto;
import org.example.eshop.dto.UpdateCartItemDto;
import org.example.eshop.dto.ViewCartDto;
import org.example.eshop.mapper.CartMapper;
import org.example.eshop.model.entities.Cart;
import org.example.eshop.model.entities.CartItem;
import org.example.eshop.model.entities.Product;
import org.example.eshop.model.entities.User;
import org.example.eshop.model.repositories.CartItemRepository;
import org.example.eshop.model.repositories.CartRepository;
import org.example.eshop.model.repositories.ProductRepository;
import org.example.eshop.model.repositories.UserRepository;
import org.example.eshop.service.CartService;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public CartServiceImpl(CartRepository cartRepository,
                           CartItemRepository cartItemRepository,
                           UserRepository userRepository,
                           ProductRepository productRepository,
                           CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public ViewCartDto create(CreateCartDto createCartDto) {
        User user = userRepository.findById(createCartDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found: " + createCartDto.getUserId()));

        if (cartRepository.findByUser_Id(user.getId()).isPresent()) {
            throw new IllegalArgumentException("Cart already exists for user: " + user.getId());
        }

        Cart cart = new Cart();
        cart.setUser(user);
        Cart saved = cartRepository.save(cart);

        return cartMapper.toViewCartDto(saved);
    }

    @Override
    @Transactional
    public ViewCartDto getById(UUID id) {
        return cartRepository.findById(id)
                .map(cartMapper::toViewCartDto)
                .orElseThrow(() -> new NotFoundException("Cart not found: " + id));
    }

    @Override
    @Transactional
    public ViewCartDto getByUserId(UUID userId) {
        return cartRepository.findByUser_Id(userId)
                .map(cartMapper::toViewCartDto)
                .orElseThrow(() -> new NotFoundException("Cart not found for user: " + userId));
    }

    @Override
    @Transactional
    public ViewCartDto addItem(UUID cartId, AddCartItemDto addCartItemDto) {
        if (addCartItemDto.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0.");
        }

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart not found: " + cartId));

        Product product = productRepository.findById(addCartItemDto.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found: " + addCartItemDto.getProductId()));

        CartItem existing = cart.getItems().stream()
                .filter(i -> i.getProduct() != null && Objects.equals(i.getProduct().getId(), product.getId()))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            existing.setQuantity(existing.getQuantity() + addCartItemDto.getQuantity());
        } else {
            CartItem item = new CartItem();
            item.setProduct(product);
            item.setQuantity(addCartItemDto.getQuantity());
            cart.addItem(item);
        }

        return cartMapper.toViewCartDto(cartRepository.save(cart));
    }

    @Override
    public ViewCartDto updateItem(UUID cartId, UUID itemId, UpdateCartItemDto updateCartItemDto) {
        if (updateCartItemDto.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0.");
        }

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart not found: " + cartId));

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Cart item not found: " + itemId));

        if (item.getCart() == null || !Objects.equals(item.getCart().getId(), cart.getId())) {
            throw new IllegalArgumentException("Cart item does not belong to cart.");
        }

        item.setQuantity(updateCartItemDto.getQuantity());
        cartItemRepository.save(item);

        return cartMapper.toViewCartDto(cart);
    }

    @Override
    public ViewCartDto removeItem(UUID cartId, UUID itemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart not found: " + cartId));

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Cart item not found: " + itemId));

        if (item.getCart() == null || !Objects.equals(item.getCart().getId(), cart.getId())) {
            throw new IllegalArgumentException("Cart item does not belong to cart.");
        }

        cart.removeItem(item);
        cartRepository.save(cart);

        return cartMapper.toViewCartDto(cart);
    }

    @Override
    public void delete(UUID cartId) {
        if (!cartRepository.existsById(cartId)) {
            throw new NotFoundException("Cart not found: " + cartId);
        }
        cartRepository.deleteById(cartId);
    }
}