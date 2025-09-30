package com.example.kafkachat.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    @KafkaListener(topicPattern = "chatroom-.*", groupId = "chat-group")
    public void listen(String message) {
        System.out.println("Kafka received: " + message);
        // You can later push this to WebSocket sessions
    }
}
