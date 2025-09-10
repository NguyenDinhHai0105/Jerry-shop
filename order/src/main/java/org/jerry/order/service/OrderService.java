package org.jerry.order.service;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.jerry.common.dto.order.OrderCreateMessage;
import org.jerry.order.client.customer.CustomerClient;
import org.jerry.order.client.product.ProductClient;
import org.jerry.order.dto.OrderLineResponse;
import org.jerry.order.dto.OrderRequest;
import org.jerry.order.dto.OrderResponse;
import org.jerry.order.dto.product.PurchaseResponse;
import org.jerry.order.entity.Order;
import org.jerry.order.entity.OrderLine;
import org.jerry.order.entity.QOrder;
import org.jerry.order.entity.QOrderLine;
import org.jerry.order.exception.BusinessException;
import org.jerry.order.exception.OrderNotFoundException;
import org.jerry.order.kafka.OrderProducer;
import org.jerry.order.kafka.message.OrderConfirmationMessage;
import org.jerry.order.repository.OrderLineRepository;
import org.jerry.order.repository.OrderRepository;
import org.jerry.order.util.OrderLineMapper;
import org.jerry.order.util.OrderMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Value("${topic.order.created}")
    private String orderCreatedTopic;

    private final OrderRepository orderRepository;
    private final OrderLineRepository orderLineRepository;
    private final OrderMapper orderMapper;
    private final OrderLineMapper orderLineMapper;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderProducer orderProducer;
    private final EntityManager entityManager;

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        // validate customer
        var customer = customerClient.getCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Customer not found with id: " + orderRequest.customerId()));

        // Create and save order
        Order order = orderMapper.toEntity(orderRequest);

        List<PurchaseResponse> purchaseProducts;
        orderProducer.sendOrderCreated(orderCreatedTopic, new OrderCreateMessage(order.getId(), orderRequest.products()));
        Order savedOrder = orderRepository.save(order);

        // Create and save order lines
        List<OrderLine> orderLines = null;
        if (orderRequest.products() != null && !orderRequest.products().isEmpty()) {
            orderLines = orderRequest.products().stream()
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

        orderProducer.sendOrderConfirmation("ORDER_CONFIRMATION",
                new OrderConfirmationMessage(
                        orderResponse.id(),
                        orderRequest.reference(),
                        orderRequest.totalAmount(),
                        orderRequest.paymentMethod(),
                        customer,
                        null
                ));

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
        JPAQuery<?> query = new JPAQuery<>(entityManager);
        QOrder qOrder = QOrder.order;
        QOrderLine qOrderLine = QOrderLine.orderLine;

        List<Tuple> results = query.select(qOrder, qOrderLine)
                .from(qOrder)
                .leftJoin(qOrderLine)
                .on(qOrder.id.eq(qOrderLine.orderId))
                .where(qOrder.id.eq(orderId))
                .fetch();

        return results.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(qOrder),
                        Collectors.mapping(
                                tuple -> tuple.get(qOrderLine),
                                Collectors.filtering(Objects::nonNull, Collectors.toList())
                        )
                ))
                .entrySet()
                .stream()
                .map(entry -> getOrderResponse(entry.getKey(), entry.getValue()))
                .findFirst()
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));
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
        JPAQuery<?> query = new JPAQuery<>(entityManager);
        // You can add custom query logic here if needed using QueryDSL
        QOrder qOrder = QOrder.order;
        QOrderLine qOrderLine = QOrderLine.orderLine;

        List<Tuple> results = query.select(qOrder, qOrderLine)
                .from(qOrder)
                .leftJoin(qOrderLine)
                .on(qOrder.id.eq(qOrderLine.orderId))
                .fetch();

        return results.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(qOrder),
                        Collectors.mapping(
                                tuple -> tuple.get(qOrderLine),
                                Collectors.filtering(Objects::nonNull, Collectors.toList())
                        )
                ))
                .entrySet()
                .stream()
                .map(entry -> getOrderResponse(entry.getKey(), entry.getValue()))
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
        if (orderRequest.products() != null) {
            // Delete existing order lines
            orderLineRepository.deleteAll(orderLineRepository.findByOrderId(orderId));

            // Create new order lines
            List<OrderLine> newOrderLines = orderRequest.products().stream()
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
