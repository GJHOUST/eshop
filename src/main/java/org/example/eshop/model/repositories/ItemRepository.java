package org.example.eshop.model.repositories;

import org.example.eshop.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Product, UUID> {
}
