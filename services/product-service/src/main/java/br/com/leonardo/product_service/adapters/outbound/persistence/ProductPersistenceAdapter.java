package br.com.leonardo.product_service.adapters.outbound.persistence;

import br.com.leonardo.product_service.domain.Product;
import br.com.leonardo.product_service.domain.ProductRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProductPersistenceAdapter implements ProductRepositoryPort {

    private final ProductJpaRepository repository;

    public ProductPersistenceAdapter(ProductJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean existsBySku(String sku) {
        return repository.existsBySku(sku);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
