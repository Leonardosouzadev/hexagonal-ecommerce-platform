package br.com.leonardo.product_service.adapters.inbound.rest;

import br.com.leonardo.product_service.application.ProductService;
import br.com.leonardo.product_service.domain.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Product Management API")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "SKU already exists")
    })
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody CreateProductRequest request) {
        Product product = productService.createProduct(
                request.name(),
                request.description(),
                request.price(),
                request.stock(),
                request.category(),
                request.sku()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProductResponse.fromProduct(product));
    }

    @GetMapping
    @Operation(summary = "List all products")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    public ResponseEntity<List<ProductResponse>> findAll() {
        List<Product> products = productService.findAll();
        List<ProductResponse> responses = products.stream()
                .map(ProductResponse::fromProduct)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResponse> findById(
            @Parameter(description = "Product UUID")
            @PathVariable UUID id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok(ProductResponse.fromProduct(product));
    }

    @GetMapping("/sku/{sku}")
    @Operation(summary = "Get product by SKU")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResponse> findBySku(
            @Parameter(description = "Product SKU")
            @PathVariable String sku) {
        Product product = productService.findBySku(sku);
        return ResponseEntity.ok(ProductResponse.fromProduct(product));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update entire product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<ProductResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody CreateProductRequest request) {
        Product product = productService.updateProduct(id, request);
        return ResponseEntity.ok(ProductResponse.fromProduct(product));
    }

    @PatchMapping("/{id}/price")
    @Operation(summary = "Update product price")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResponse> updatePrice(
            @PathVariable UUID id,
            @Parameter(description = "New price")
            @RequestParam BigDecimal price) {
        Product product = productService.updatePrice(id, price);
        return ResponseEntity.ok(ProductResponse.fromProduct(product));
    }

    @PatchMapping("/{id}/stock")
    @Operation(summary = "Update product stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResponse> updateStock(
            @PathVariable UUID id,
            @Parameter(description = "New stock quantity")
            @RequestParam Integer stock) {
        Product product = productService.updateStock(id, stock);
        return ResponseEntity.ok(ProductResponse.fromProduct(product));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft delete product (deactivate)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get products by category")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    public ResponseEntity<List<ProductResponse>> findByCategory(
            @Parameter(description = "Product category")
            @PathVariable String category) {
        List<Product> products = productService.findByCategory(category);
        List<ProductResponse> responses = products.stream()
                .map(ProductResponse::fromProduct)
                .toList();
        return ResponseEntity.ok(responses);
    }
}

