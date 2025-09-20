package org.jerry.common.dto.product;

import java.math.BigDecimal;
import java.util.UUID;

public record PurchaseResponse(
        UUID productId,
        String name,
        String description,
        BigDecimal price,
        Double quantity) {
}
