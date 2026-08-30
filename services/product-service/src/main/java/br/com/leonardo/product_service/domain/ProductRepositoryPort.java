package br.com.leonardo.product_service.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepositoryPort {
    Product save(Product product);
    Optional<Product> findById(UUID id);
    Optional<Product> findBySku(String sku);
    List<Product> findAll();
    List<Product> findByCategory(String category);
    boolean existsBySku(String sku);
    void deleteById(UUID id);
}
