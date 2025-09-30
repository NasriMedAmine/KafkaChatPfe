package com.example.kafkachat.controller;

import com.example.kafkachat.Models.User;
import com.example.kafkachat.service.KafkaProducerService;
import com.example.kafkachat.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final KafkaProducerService producer;
    private final UserService userService;
    private final Map<String, WebSocketSession> sessions = new HashMap<>();

    public ChatWebSocketHandler(KafkaProducerService producer, UserService userService) {
        this.producer = producer;
        this.userService = userService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String username = getUsername(session);
        sessions.put(username, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String[] parts = message.getPayload().split(":", 3);
        String sender = parts[0];
        String receiver = parts[1];
        String content = parts[2];

        User senderUser = userService.getUserByUsername(sender);
        User receiverUser = userService.getUserByUsername(receiver);

        if (senderUser == null || receiverUser == null) return;
        if (!canChat(senderUser.getRole(), receiverUser.getRole())) return;

        String topic = "chatroom-" + sender + "-" + receiver;
        producer.sendMessage(topic, sender + ": " + content);

        WebSocketSession receiverSession = sessions.get(receiver);
        if (receiverSession != null && receiverSession.isOpen()) {
            receiverSession.sendMessage(new TextMessage(sender + ": " + content));
        }
    }

    private String getUsername(WebSocketSession session) {
        String query = session.getUri().getQuery();
        return query.split("=")[1];
    }

    private boolean canChat(String roleA, String roleB) {
        List<String> group1 = List.of("MODERATEUR", "SOURCING", "COMMERCE", "ADV");
        List<String> group2 = List.of("CLIENT", "CANDIDAT", "AMBASSADEUR", "SOURCING_COMMERCE", "AUTRE");

        // Group 1 ↔ Group 1
        if (group1.contains(roleA) && group1.contains(roleB)) return true;

        // Group 1 ↔ Group 2
        if ((group1.contains(roleA) && group2.contains(roleB)) ||
                (group2.contains(roleA) && group1.contains(roleB))) return true;

        // Admin ↔ Group 1
        if ((roleA.equals("ADMIN") && group1.contains(roleB)) ||
                (roleB.equals("ADMIN") && group1.contains(roleA))) return true;

        // All other combinations are not allowed
        return false;
    }

}
