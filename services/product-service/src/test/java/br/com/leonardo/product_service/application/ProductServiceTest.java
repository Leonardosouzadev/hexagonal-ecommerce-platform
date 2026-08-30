package br.com.leonardo.product_service.application;

import br.com.leonardo.product_service.adapters.inbound.rest.CreateProductRequest;
import br.com.leonardo.product_service.adapters.inbound.rest.DuplicateSkuException;
import br.com.leonardo.product_service.adapters.inbound.rest.ProductNotFoundException;
import br.com.leonardo.product_service.domain.Product;
import br.com.leonardo.product_service.domain.ProductEventPublisherPort;
import br.com.leonardo.product_service.domain.ProductRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService Tests")
class ProductServiceTest {

    @Mock
    private ProductRepositoryPort productRepository;

    @Mock
    private ProductEventPublisherPort productEventPublisher;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;
    private CreateProductRequest validRequest;

    @BeforeEach
    void setUp() {
        testProduct = new Product(
                "Test Product",
                "A test product description",
                new BigDecimal("99.99"),
                100,
                "Electronics",
                "TEST-SKU-001"
        );

        validRequest = new CreateProductRequest(
                "Test Product",
                "A test product description",
                new BigDecimal("99.99"),
                100,
                "Electronics",
                "TEST-SKU-001"
        );
    }

    @Test
    @DisplayName("Should create product successfully")
    void testCreateProductSuccess() {
        when(productRepository.existsBySku(anyString())).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        Product result = productService.createProduct(
                validRequest.name(),
                validRequest.description(),
                validRequest.price(),
                validRequest.stock(),
                validRequest.category(),
                validRequest.sku()
        );

        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        verify(productRepository).save(any(Product.class));
        verify(productEventPublisher).publishProductCreated(any(Product.class));
    }

    @Test
    @DisplayName("Should throw exception when SKU already exists")
    void testCreateProductWithDuplicateSku() {
        when(productRepository.existsBySku(anyString())).thenReturn(true);

        assertThrows(DuplicateSkuException.class, () ->
                productService.createProduct(
                        validRequest.name(),
                        validRequest.description(),
                        validRequest.price(),
                        validRequest.stock(),
                        validRequest.category(),
                        validRequest.sku()
                )
        );

        verify(productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find all products")
    void testFindAll() {
        List<Product> products = List.of(testProduct);
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository).findAll();
    }

    @Test
    @DisplayName("Should find product by ID")
    void testFindById() {
        UUID productId = UUID.randomUUID();
        testProduct.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));

        Product result = productService.findById(productId);

        assertNotNull(result);
        assertEquals(productId, result.getId());
        verify(productRepository).findById(productId);
    }

    @Test
    @DisplayName("Should throw exception when product not found by ID")
    void testFindByIdNotFound() {
        UUID productId = UUID.randomUUID();
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () ->
                productService.findById(productId)
        );
    }

    @Test
    @DisplayName("Should find product by SKU")
    void testFindBySku() {
        when(productRepository.findBySku("TEST-SKU-001")).thenReturn(Optional.of(testProduct));

        Product result = productService.findBySku("TEST-SKU-001");

        assertNotNull(result);
        assertEquals("TEST-SKU-001", result.getSku());
        verify(productRepository).findBySku("TEST-SKU-001");
    }

    @Test
    @DisplayName("Should update product price")
    void testUpdatePrice() {
        UUID productId = UUID.randomUUID();
        testProduct.setId(productId);
        BigDecimal newPrice = new BigDecimal("149.99");

        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        Product result = productService.updatePrice(productId, newPrice);

        assertNotNull(result);
        verify(productRepository).findById(productId);
        verify(productRepository).save(any(Product.class));
        verify(productEventPublisher).publishProductPriceChanged(any(Product.class));
    }

    @Test
    @DisplayName("Should update product stock")
    void testUpdateStock() {
        UUID productId = UUID.randomUUID();
        testProduct.setId(productId);
        Integer newStock = 50;

        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        Product result = productService.updateStock(productId, newStock);

        assertNotNull(result);
        verify(productRepository).findById(productId);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("Should delete product (soft delete)")
    void testDeleteProduct() {
        UUID productId = UUID.randomUUID();
        testProduct.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(testProduct));
        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        productService.deleteProduct(productId);

        verify(productRepository).findById(productId);
        verify(productRepository).save(any(Product.class));
        verify(productEventPublisher).publishProductDeleted(any(Product.class));
    }

    @Test
    @DisplayName("Should find products by category")
    void testFindByCategory() {
        List<Product> products = List.of(testProduct);
        when(productRepository.findByCategory("Electronics")).thenReturn(products);

        List<Product> result = productService.findByCategory("Electronics");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(productRepository).findByCategory("Electronics");
    }
}
