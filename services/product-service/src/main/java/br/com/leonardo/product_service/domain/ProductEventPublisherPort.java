package br.com.leonardo.product_service.domain;

import java.util.UUID;

public interface ProductEventPublisherPort {
    void publishProductCreated(Product product);
    void publishProductUpdated(Product product);
    void publishProductPriceChanged(Product product);
    void publishProductDeleted(UUID productId);
}
