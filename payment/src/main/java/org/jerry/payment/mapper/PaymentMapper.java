package org.jerry.payment.mapper;

import org.jerry.payment.dto.PaymentRequest;
import org.jerry.payment.dto.PaymentResponse;
import org.jerry.payment.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentMapper {

    public Payment toEntity(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setAmount(request.amount());
        payment.setPaymentMethod(request.paymentMethod());
        if (request.orderId() != null) {
            // Create a deterministic UUID from orderId or use a random UUID
            payment.setOrderId(UUID.randomUUID()); // You might want to implement proper orderId to UUID mapping
        }
        return payment;
    }

    public PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getOrderId(),
                null, // orderReference - would need to be fetched from order service
                null, // customer - would need to be fetched from customer service
                payment.getCreatedDate(),
                payment.getLastModifiedDate()
        );
    }

    public void updateEntity(Payment payment, PaymentRequest request) {
        payment.setAmount(request.amount());
        payment.setPaymentMethod(request.paymentMethod());
        if (request.orderId() != null) {
            payment.setOrderId(UUID.randomUUID()); // You might want to implement proper orderId to UUID mapping
        }
    }
}
