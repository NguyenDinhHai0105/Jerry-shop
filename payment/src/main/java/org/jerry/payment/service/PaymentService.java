package org.jerry.payment.service;

import lombok.RequiredArgsConstructor;
import org.jerry.common.dto.notification.PaymentNotificationRequest;
import org.jerry.common.service.KafkaService;
import org.jerry.payment.dto.PaymentRequest;
import org.jerry.payment.dto.PaymentResponse;
import org.jerry.payment.entity.Payment;
import org.jerry.payment.exception.PaymentNotFoundException;
import org.jerry.payment.mapper.PaymentMapper;
import org.jerry.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final KafkaService<UUID, PaymentNotificationRequest> kafkaService;

    public PaymentResponse createPayment(PaymentRequest request) {
        Payment payment = paymentMapper.toEntity(request);
        Payment savedPayment = paymentRepository.save(payment);
        // Send payment notification
        PaymentNotificationRequest notificationRequest = PaymentNotificationRequest.builder()
                .orderReference(request.orderReference())
                .amount(request.totalAmount())
                .paymentMethod(request.paymentMethod())
                .customerFirstName(request.customer().firstName())
                .customerLastName(request.customer().lastName())
                .customerEmail(request.customer().email())
                .build();
        kafkaService.sendMessage("NOTIFICATION_PAYMENT_TOPIC", savedPayment.getOrderId(), notificationRequest);
        return paymentMapper.toResponse(savedPayment);
    }

    public PaymentResponse updatePayment(UUID id, PaymentRequest request) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));

        paymentMapper.updateEntity(payment, request);
        Payment updatedPayment = paymentRepository.save(payment);
        return paymentMapper.toResponse(updatedPayment);
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(UUID id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));
        return paymentMapper.toResponse(payment);
    }
}
