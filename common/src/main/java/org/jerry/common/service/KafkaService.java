package org.jerry.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService<K, T> {
    private final KafkaTemplate<K,T> kafkaTemplate;
    public void sendMessage(String topic, K key, T message) {
        kafkaTemplate.send(topic, key, message);
    }
}
