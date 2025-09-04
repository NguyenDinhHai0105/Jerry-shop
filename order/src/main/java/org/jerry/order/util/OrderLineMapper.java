package org.jerry.order.util;

import org.jerry.order.dto.OrderLineRequest;
import org.jerry.order.dto.OrderLineResponse;
import org.jerry.order.entity.OrderLine;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderLineMapper {

    public OrderLine toEntity(OrderLineRequest request, UUID orderId) {
        OrderLine orderLine = new OrderLine();
        orderLine.setId(UUID.randomUUID());
        orderLine.setOrderId(orderId);
        orderLine.setProductId(request.productId());
        orderLine.setQuantity(request.quantity());
        return orderLine;
    }

    public OrderLineResponse toResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getOrderId(),
                orderLine.getProductId(),
                orderLine.getQuantity()
        );
    }
}
