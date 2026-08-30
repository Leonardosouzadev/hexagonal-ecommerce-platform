package br.com.leonardo.product_service.adapters.outbound.messaging.kafka;

import br.com.leonardo.product_service.adapters.outbound.messaging.dto.ProductCreatedEvent;
import br.com.leonardo.product_service.domain.Product;
import br.com.leonardo.product_service.domain.ProductEventPublisherPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class KafkaProductEventPublisher implements ProductEventPublisherPort {

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
                    product.getSku()
            );

            kafkaTemplate.send("product-created", objectMapper.writeValueAsString(event));
        } catch (Exception e) {
            throw new IllegalStateException("Error publishing product-created event", e);
        }
    }

    @Override
    public void publishProductUpdated(Product product) {
        // sample placeholder for future product-updated topic
    }

    @Override
    public void publishProductPriceChanged(Product product) {
        // sample placeholder for future product-price-changed topic
    }

    @Override
    public void publishProductDeleted(UUID productId) {
        // sample placeholder for future product-deleted topic
    }
}
