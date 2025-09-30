/*
package com.example.kafkachat;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class ChatConsumer {
    @KafkaListener(topics = {"private-*", "private-admin-*"}, groupId = "chat-group")
    public void listen(String key, String value) {
        System.out.println(LocalDateTime.now() + " [CHAT] From " + key + ": " + value);
    }
}*/
