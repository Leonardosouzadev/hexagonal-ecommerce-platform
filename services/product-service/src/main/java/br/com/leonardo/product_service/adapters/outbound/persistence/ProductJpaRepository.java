package br.com.leonardo.product_service.adapters.outbound.persistence;

import br.com.leonardo.product_service.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<Product, UUID> {
    boolean existsBySku(String sku);
    
    Optional<Product> findBySku(String sku);
    
    List<Product> findByCategory(String category);
    
    @Query("SELECT p FROM Product p WHERE p.active = true ORDER BY p.createdAt DESC")
    List<Product> findAllActive();
}
