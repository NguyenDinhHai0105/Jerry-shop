package org.jerry.payment.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.jerry.payment.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer id,
        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        BigDecimal amount,
        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,
        @NotNull(message = "Order ID is required")
        Integer orderId,
        String orderReference,
        @Valid
        Customer customer
) {
}
