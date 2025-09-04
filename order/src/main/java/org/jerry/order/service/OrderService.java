package org.jerry.order.service;

import lombok.RequiredArgsConstructor;
import org.jerry.order.dto.OrderLineResponse;
import org.jerry.order.dto.OrderRequest;
import org.jerry.order.dto.OrderResponse;
import org.jerry.order.entity.Order;
import org.jerry.order.entity.OrderLine;
import org.jerry.order.exception.OrderNotFoundException;
import org.jerry.order.repository.OrderLineRepository;
import org.jerry.order.repository.OrderRepository;
import org.jerry.order.util.OrderLineMapper;
import org.jerry.order.util.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final OrderMapper orderMapper;
    private final OrderLineMapper orderLineMapper;

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        // Create and save order
        Order order = orderMapper.toEntity(orderRequest);
        Order savedOrder = orderRepository.save(order);

        // Create and save order lines
        List<OrderLine> orderLines = null;
        if (orderRequest.orderLines() != null && !orderRequest.orderLines().isEmpty()) {
            orderLines = orderRequest.orderLines().stream()
                    .map(lineRequest -> orderLineMapper.toEntity(lineRequest, savedOrder.getId()))
                    .collect(Collectors.toList());
            orderLineRepository.saveAll(orderLines);
        }

        // Convert to response
        OrderResponse orderResponse = orderMapper.toResponse(savedOrder);
        List<OrderLineResponse> orderLineResponses = orderLines != null ?
                orderLines.stream()
                        .map(orderLineMapper::toResponse)
                        .collect(Collectors.toList()) : null;

        return new OrderResponse(
                orderResponse.id(),
                orderResponse.reference(),
                orderResponse.totalAmount(),
                orderResponse.paymentMethod(),
                orderResponse.customerId(),
                orderResponse.createdAt(),
                orderResponse.lastModifiedAt(),
                orderLineResponses
        );
    }

    public OrderResponse getOrderById(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        List<OrderLine> orderLines = orderLineRepository.findByOrderId(orderId);
        return getOrderResponse(order, orderLines);
    }

    private OrderResponse getOrderResponse(Order order, List<OrderLine> orderLines) {
        List<OrderLineResponse> orderLineResponses = orderLines.stream()
                .map(orderLineMapper::toResponse)
                .collect(Collectors.toList());

        OrderResponse orderResponse = orderMapper.toResponse(order);
        return new OrderResponse(
                orderResponse.id(),
                orderResponse.reference(),
                orderResponse.totalAmount(),
                orderResponse.paymentMethod(),
                orderResponse.customerId(),
                orderResponse.createdAt(),
                orderResponse.lastModifiedAt(),
                orderLineResponses
        );
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> {
                    List<OrderLine> orderLines = orderLineRepository.findByOrderId(order.getId());
                    return getOrderResponse(order, orderLines);
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse updateOrder(UUID orderId, OrderRequest orderRequest) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        // Update order fields
        existingOrder.setReference(orderRequest.reference());
        existingOrder.setTotalAmount(orderRequest.totalAmount());
        existingOrder.setPaymentMethod(orderRequest.paymentMethod());
        existingOrder.setCustomerId(orderRequest.customerId());

        Order updatedOrder = orderRepository.save(existingOrder);

        // Update order lines if provided
        if (orderRequest.orderLines() != null) {
            // Delete existing order lines
            orderLineRepository.deleteAll(orderLineRepository.findByOrderId(orderId));

            // Create new order lines
            List<OrderLine> newOrderLines = orderRequest.orderLines().stream()
                    .map(lineRequest -> orderLineMapper.toEntity(lineRequest, orderId))
                    .collect(Collectors.toList());
            orderLineRepository.saveAll(newOrderLines);
        }

        return getOrderById(orderId);
    }

    @Transactional
    public void deleteOrder(UUID orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException("Order not found with id: " + orderId);
        }

        // Delete order lines first
        orderLineRepository.deleteAll(orderLineRepository.findByOrderId(orderId));

        // Delete order
        orderRepository.deleteById(orderId);
    }
}
