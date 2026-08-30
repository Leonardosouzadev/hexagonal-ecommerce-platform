package br.com.leonardo.product_service.adapters.inbound.rest;

import br.com.leonardo.product_service.application.ProductService;
import br.com.leonardo.product_service.domain.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody CreateProductRequest request) {
        Product product = productService.createProduct(
                request.name(),
                request.description(),
                request.price(),
                request.stock(),
                request.category(),
                request.sku()
        );
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PatchMapping("/{id}/price")
    public ResponseEntity<Product> updatePrice(@PathVariable UUID id, @RequestParam BigDecimal price) {
        return ResponseEntity.ok(productService.updatePrice(id, price));
    }
}
