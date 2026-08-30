package br.com.leonardo.customer_service.application.dto;

import br.com.leonardo.customer_service.domain.Address;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record AddressResponse(
        @JsonProperty("id")
        UUID id,

        @JsonProperty("street")
        String street,

        @JsonProperty("number")
        String number,

        @JsonProperty("complement")
        String complement,

        @JsonProperty("city")
        String city,

        @JsonProperty("state")
        String state,

        @JsonProperty("zip_code")
        String zipCode,

        @JsonProperty("country")
        String country,

        @JsonProperty("is_default")
        boolean isDefault,

        @JsonProperty("created_at")
        LocalDateTime createdAt,

        @JsonProperty("updated_at")
        LocalDateTime updatedAt
) {
    public static AddressResponse fromAddress(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getCity(),
                address.getState(),
                address.getZipCode(),
                address.getCountry(),
                address.isDefault(),
                address.getCreatedAt(),
                address.getUpdatedAt()
        );
    }
}
