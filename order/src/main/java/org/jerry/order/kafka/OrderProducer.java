package org.jerry.order.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jerry.common.dto.order.OrderCreateMessage;
import org.jerry.common.service.KafkaService;
import org.jerry.order.kafka.message.OrderConfirmationMessage;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {
    private final KafkaService<UUID, Object> kafkaService;

    public void sendOrderConfirmation(String topic, OrderConfirmationMessage orderConfirmationMessage) {
        log.info("Producing message to topic: {}", topic);
        kafkaService.sendMessage(topic, orderConfirmationMessage.orderId(), orderConfirmationMessage);
    }

    public void sendOrderCreated(String topic, OrderCreateMessage orderCreateMessage) {
        log.info("Producing message to topic: {}", topic);
        kafkaService.sendMessage(topic, orderCreateMessage.getOrderId(), orderCreateMessage);
    }
}
