package org.jerry.order.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jerry.order.kafka.message.OrderConfirmationMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {
    private final KafkaTemplate<String, Message<OrderConfirmationMessage>> kafkaTemplate;

    public void sendOrderConfirmation(String topic, OrderConfirmationMessage orderConfirmationMessage) {
        log.info("Producing message to topic: {}", topic);
        Message<OrderConfirmationMessage> message = MessageBuilder
                .withPayload(orderConfirmationMessage)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();
        kafkaTemplate.send(topic, message);
    }
}
