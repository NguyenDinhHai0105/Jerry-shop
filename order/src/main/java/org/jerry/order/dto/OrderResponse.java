package org.jerry.order.dto;

import org.jerry.common.constant.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        String reference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        UUID customerId,
        LocalDateTime createdAt,
        LocalDateTime lastModifiedAt,
        List<OrderLineResponse> product
) {
}
