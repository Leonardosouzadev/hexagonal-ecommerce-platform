package br.com.leonardo.product_service.adapters.outbound.messaging.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductPriceChangedEvent(
        @JsonProperty("product_id")
        UUID productId,

        @JsonProperty("new_price")
        BigDecimal newPrice,

        @JsonProperty("changed_at")
        LocalDateTime changedAt
) {
}
