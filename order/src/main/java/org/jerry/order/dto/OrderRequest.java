package org.jerry.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.jerry.common.constant.PaymentMethod;
import org.jerry.common.dto.request.PurchaseRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderRequest(
        @NotNull(message = "Reference is required")
        String reference,

        @NotNull(message = "Total amount is required")
        @Positive(message = "Total amount must be positive")
        BigDecimal totalAmount,

        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,

        @NotNull(message = "Customer ID is required")
        UUID customerId,

        @Valid
        List<PurchaseRequest> products
) {
}
