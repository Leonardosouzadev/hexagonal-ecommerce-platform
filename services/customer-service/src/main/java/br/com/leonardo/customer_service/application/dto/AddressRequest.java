package br.com.leonardo.customer_service.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AddressRequest(
        @NotBlank(message = "Street is required")
        @Size(min = 3, max = 200, message = "Street must be between 3 and 200 characters")
        String street,

        @NotBlank(message = "Number is required")
        @Size(min = 1, max = 10, message = "Number must be between 1 and 10 characters")
        String number,

        @Size(max = 100, message = "Complement must not exceed 100 characters")
        String complement,

        @NotBlank(message = "City is required")
        @Size(min = 2, max = 100, message = "City must be between 2 and 100 characters")
        String city,

        @NotBlank(message = "State is required")
        @Size(min = 2, max = 50, message = "State must be between 2 and 50 characters")
        String state,

        @NotBlank(message = "ZipCode is required")
        @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "ZipCode must be in format XXXXX-XXX")
        String zipCode,

        @NotBlank(message = "Country is required")
        @Size(min = 2, max = 100, message = "Country must be between 2 and 100 characters")
        String country
) {
}
