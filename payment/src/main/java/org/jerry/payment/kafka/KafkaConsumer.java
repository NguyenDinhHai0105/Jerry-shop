package org.jerry.payment.kafka;

import org.jerry.payment.dto.PaymentRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "ORDER_CONFIRMATION", groupId = "payment-service-group")
    public void consumeOrderConfirmation(PaymentRequest request) {
        // Placeholder for future implementation
        System.out.println("Received order confirmation: " + request);
    }
}
