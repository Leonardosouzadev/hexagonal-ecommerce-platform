package br.com.leonardo.product_service.application;

import br.com.leonardo.product_service.adapters.inbound.rest.CreateProductRequest;
import br.com.leonardo.product_service.adapters.inbound.rest.DuplicateSkuException;
import br.com.leonardo.product_service.adapters.inbound.rest.ProductNotFoundException;
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
            throw new DuplicateSkuException("SKU '" + sku + "' already exists");
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
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product with ID '" + id + "' not found"
                ));
    }

    public Product findBySku(String sku) {
        return productRepository.findBySku(sku)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Product with SKU '" + sku + "' not found"
                ));
    }

    public List<Product> findByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public Product updateProduct(UUID id, CreateProductRequest request) {
        Product product = findById(id);

        // Check if SKU is being changed and if the new SKU already exists
        if (!product.getSku().equals(request.sku()) && productRepository.existsBySku(request.sku())) {
            throw new DuplicateSkuException("SKU '" + request.sku() + "' already exists");
        }

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());
        product.setCategory(request.category());
        product.setSku(request.sku());
        product.setUpdatedAt(java.time.LocalDateTime.now());

        Product updatedProduct = productRepository.save(product);
        productEventPublisher.publishProductUpdated(updatedProduct);

        return updatedProduct;
    }

    public Product updatePrice(UUID id, BigDecimal price) {
        Product product = findById(id);

        product.updatePrice(price);
        Product updatedProduct = productRepository.save(product);
        productEventPublisher.publishProductPriceChanged(updatedProduct);

        return updatedProduct;
    }

    public Product updateStock(UUID id, Integer stock) {
        Product product = findById(id);

        product.updateStock(stock);
        Product updatedProduct = productRepository.save(product);
        productEventPublisher.publishProductUpdated(updatedProduct);

        return updatedProduct;
    }

    public void deleteProduct(UUID id) {
        Product product = findById(id);
        product.deactivate();
        productRepository.save(product);
        productEventPublisher.publishProductDeleted(product);
    }
}
