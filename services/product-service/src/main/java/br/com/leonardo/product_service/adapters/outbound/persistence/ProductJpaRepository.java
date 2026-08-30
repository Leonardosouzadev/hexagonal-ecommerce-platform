package br.com.leonardo.product_service.adapters.outbound.persistence;

import br.com.leonardo.product_service.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<Product, UUID> {
    boolean existsBySku(String sku);
}
