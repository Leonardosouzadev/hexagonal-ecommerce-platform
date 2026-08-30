package br.com.leonardo.product_service.adapters.outbound.messaging.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProductDeletedEvent(
        @JsonProperty("product_id")
        UUID productId,

        @JsonProperty("deleted_at")
        LocalDateTime deletedAt
) {
}
