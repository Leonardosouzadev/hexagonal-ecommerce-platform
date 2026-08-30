package br.com.leonardo.product_service.application;

import br.com.leonardo.product_service.domain.Product;
import br.com.leonardo.product_service.domain.ProductEventPublisherPort;
import br.com.leonardo.product_service.domain.ProductRepositoryPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepositoryPort productRepository;
    private final ProductEventPublisherPort productEventPublisher;

    public ProductService(ProductRepositoryPort productRepository,
                          ProductEventPublisherPort productEventPublisher) {
        this.productRepository = productRepository;
        this.productEventPublisher = productEventPublisher;
    }

    public Product createProduct(String name, String description, BigDecimal price, Integer stock,
                                 String category, String sku) {
        if (productRepository.existsBySku(sku)) {
            throw new IllegalArgumentException("SKU already exists");
        }

        Product product = new Product(name, description, price, stock, category, sku);
        Product savedProduct = productRepository.save(product);
        productEventPublisher.publishProductCreated(savedProduct);

        return savedProduct;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    public Product updatePrice(UUID id, BigDecimal price) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        product.updatePrice(price);
        Product updatedProduct = productRepository.save(product);
        productEventPublisher.publishProductPriceChanged(updatedProduct);

        return updatedProduct;
    }
}
