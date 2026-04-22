package org.example.eshop.model.repositories;

import org.example.eshop.model.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByUser_Id(UUID userId);

}
