package br.com.leonardo.product_service.adapters.outbound.messaging.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductCreatedEvent(
        UUID productId,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        String category,
        String sku
) {
}
