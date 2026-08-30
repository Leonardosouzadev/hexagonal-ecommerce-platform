package br.com.leonardo.customer_service.adapters.outbound.messaging.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerCreatedEvent(
        @JsonProperty("customer_id")
        UUID customerId,

        @JsonProperty("email")
        String email,

        @JsonProperty("name")
        String name,

        @JsonProperty("phone")
        String phone,

        @JsonProperty("cpf")
        String cpf,

        @JsonProperty("created_at")
        LocalDateTime createdAt
) {
}
