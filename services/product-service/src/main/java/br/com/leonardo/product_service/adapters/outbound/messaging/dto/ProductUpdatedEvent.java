package br.com.leonardo.product_service.adapters.outbound.messaging.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductUpdatedEvent(
        @JsonProperty("product_id")
        UUID productId,

        @JsonProperty("name")
        String name,

        @JsonProperty("description")
        String description,

        @JsonProperty("price")
        BigDecimal price,

        @JsonProperty("stock")
        Integer stock,

        @JsonProperty("category")
        String category,

        @JsonProperty("sku")
        String sku,

        @JsonProperty("updated_at")
        LocalDateTime updatedAt
) {
}
