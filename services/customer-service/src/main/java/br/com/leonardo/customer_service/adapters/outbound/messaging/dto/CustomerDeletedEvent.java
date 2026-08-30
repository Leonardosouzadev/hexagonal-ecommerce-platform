package br.com.leonardo.customer_service.adapters.outbound.messaging.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerDeletedEvent(
        @JsonProperty("customer_id")
        UUID customerId,

        @JsonProperty("deleted_at")
        LocalDateTime deletedAt
) {
}
