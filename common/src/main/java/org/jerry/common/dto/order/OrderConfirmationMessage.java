package org.jerry.common.dto.order;

import org.jerry.common.constant.PaymentMethod;
import org.jerry.common.dto.customer.CustomerResponse;
import org.jerry.common.dto.product.PurchaseResponse;

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
