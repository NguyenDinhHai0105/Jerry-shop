package org.jerry.payment.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.jerry.common.constant.PaymentMethod;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequest(
        UUID id,
        @NotNull(message = "Order ID is required")
        UUID orderId,
        String orderReference,
        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        BigDecimal totalAmount,
        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,

        @Valid
        Customer customer
) {
}
