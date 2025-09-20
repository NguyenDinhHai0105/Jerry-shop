package org.jerry.payment.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record Customer(
        String id,
        @NotNull(message = "Firstname is required")
        String firstName,
        @NotNull(message = "Lastname is required")
        String lastName,
        @NotNull(message = "Email is required")
        @Email(message = "The customer email is not correctly formatted")
        String email
) {
}
