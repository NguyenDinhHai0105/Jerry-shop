package org.jerry.ecommerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.jerry.ecommerce.entity.Address;

import java.util.UUID;

public record CustomerRequest(
        UUID id,
        @NotNull(message = "First name is required")
        String firstName,
        @NotNull(message = "Last name is required")
        String lastName,
        @NotNull(message = "Email is required")
        @Email(message = "Email format should be valid")
        String email,
        Address address
) {
}
