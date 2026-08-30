package br.com.leonardo.product_service.domain;

public interface ProductEventPublisherPort {
    void publishProductCreated(Product product);
    void publishProductUpdated(Product product);
    void publishProductPriceChanged(Product product);
    void publishProductDeleted(Product product);
}
