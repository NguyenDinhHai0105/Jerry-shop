package org.jerry.order.kafka.message;

import org.jerry.order.client.customer.CustomerResponse;
import org.jerry.order.dto.product.PurchaseResponse;
import org.jerry.order.entity.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderConfirmationMessage(
        UUID orderId,
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
