package org.jerry.payment.dto;

import org.jerry.common.constant.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponse(
        UUID id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        UUID orderId,
        String orderReference,
        Customer customer,
        LocalDateTime createdDate,
        LocalDateTime lastModifiedDate
) {
}
