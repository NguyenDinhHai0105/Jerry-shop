package org.jerry.order.dto;

import java.util.UUID;

public record OrderLineResponse(
        UUID id,
        UUID orderId,
        UUID productId,
        Double quantity
) {
}
