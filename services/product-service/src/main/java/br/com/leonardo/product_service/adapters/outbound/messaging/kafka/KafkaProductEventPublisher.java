package br.com.leonardo.product_service.adapters.outbound.messaging.kafka;

import br.com.leonardo.product_service.adapters.outbound.messaging.dto.ProductCreatedEvent;
import br.com.leonardo.product_service.adapters.outbound.messaging.dto.ProductDeletedEvent;
import br.com.leonardo.product_service.adapters.outbound.messaging.dto.ProductPriceChangedEvent;
import br.com.leonardo.product_service.adapters.outbound.messaging.dto.ProductUpdatedEvent;
import br.com.leonardo.product_service.domain.Product;
import br.com.leonardo.product_service.domain.ProductEventPublisherPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class KafkaProductEventPublisher implements ProductEventPublisherPort {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProductEventPublisher.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProductEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void publishProductCreated(Product product) {
        try {
            ProductCreatedEvent event = new ProductCreatedEvent(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStock(),
                    product.getCategory(),
                    product.getSku(),
                    product.getCreatedAt()
            );

            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("product-created", product.getId().toString(), message);
            logger.info("Product created event published: {}", product.getId());
        } catch (Exception e) {
            logger.error("Error publishing product-created event", e);
            throw new IllegalStateException("Error publishing product-created event", e);
        }
    }

    @Override
    public void publishProductUpdated(Product product) {
        try {
            ProductUpdatedEvent event = new ProductUpdatedEvent(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStock(),
                    product.getCategory(),
                    product.getSku(),
                    product.getUpdatedAt()
            );

            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("product-updated", product.getId().toString(), message);
            logger.info("Product updated event published: {}", product.getId());
        } catch (Exception e) {
            logger.error("Error publishing product-updated event", e);
            throw new IllegalStateException("Error publishing product-updated event", e);
        }
    }

    @Override
    public void publishProductPriceChanged(Product product) {
        try {
            ProductPriceChangedEvent event = new ProductPriceChangedEvent(
                    product.getId(),
                    product.getPrice(),
                    product.getUpdatedAt()
            );

            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("product-price-changed", product.getId().toString(), message);
            logger.info("Product price changed event published: {}", product.getId());
        } catch (Exception e) {
            logger.error("Error publishing product-price-changed event", e);
            throw new IllegalStateException("Error publishing product-price-changed event", e);
        }
    }

    @Override
    public void publishProductDeleted(Product product) {
        try {
            ProductDeletedEvent event = new ProductDeletedEvent(
                    product.getId(),
                    product.getUpdatedAt()
            );

            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("product-deleted", product.getId().toString(), message);
            logger.info("Product deleted event published: {}", product.getId());
        } catch (Exception e) {
            logger.error("Error publishing product-deleted event", e);
            throw new IllegalStateException("Error publishing product-deleted event", e);
        }
    }
}
