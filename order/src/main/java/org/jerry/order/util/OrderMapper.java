package org.jerry.order.util;

import org.jerry.order.dto.OrderRequest;
import org.jerry.order.dto.OrderResponse;
import org.jerry.order.entity.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderMapper {

    public Order toEntity(OrderRequest request) {
        Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setReference(request.reference());
        order.setTotalAmount(request.totalAmount());
        order.setPaymentMethod(request.paymentMethod());
        order.setCustomerId(request.customerId());
        return order;
    }

    public OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId(),
                order.getCreatedAt(),
                order.getLastModifiedAt(),
                null // OrderLines will be handled separately
        );
    }
}
