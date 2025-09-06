package org.jerry.order.kafka.message;

import org.jerry.order.client.customer.CustomerResponse;
import org.jerry.order.dto.product.PurchaseResponse;
import org.jerry.order.entity.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmationMessage(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
