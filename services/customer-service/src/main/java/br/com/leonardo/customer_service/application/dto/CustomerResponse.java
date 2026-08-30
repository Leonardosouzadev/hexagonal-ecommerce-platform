package br.com.leonardo.customer_service.application.dto;

import br.com.leonardo.customer_service.domain.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CustomerResponse(
        @JsonProperty("id")
        UUID id,

        @JsonProperty("email")
        String email,

        @JsonProperty("name")
        String name,

        @JsonProperty("phone")
        String phone,

        @JsonProperty("cpf")
        String cpf,

        @JsonProperty("addresses")
        List<AddressResponse> addresses,

        @JsonProperty("active")
        boolean active,

        @JsonProperty("created_at")
        LocalDateTime createdAt,

        @JsonProperty("updated_at")
        LocalDateTime updatedAt
) {
    public static CustomerResponse fromCustomer(Customer customer) {
        List<AddressResponse> addressResponses = customer.getAddresses()
                .stream()
                .map(AddressResponse::fromAddress)
                .toList();

        return new CustomerResponse(
                customer.getId(),
                customer.getEmail(),
                customer.getName(),
                customer.getPhone(),
                customer.getCpf(),
                addressResponses,
                customer.isActive(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }
}
