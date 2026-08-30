package br.com.leonardo.customer_service.adapters.outbound.messaging.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerUpdatedEvent(
        @JsonProperty("customer_id")
        UUID customerId,

        @JsonProperty("name")
        String name,

        @JsonProperty("phone")
        String phone,

        @JsonProperty("updated_at")
        LocalDateTime updatedAt
) {
}
