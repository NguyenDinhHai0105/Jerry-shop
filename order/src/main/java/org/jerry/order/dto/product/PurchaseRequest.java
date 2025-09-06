package org.jerry.order.dto.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record PurchaseRequest(
        @NotNull(message = "Product ID is required")
        UUID productId,

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be positive")
        Double quantity
) {
}
