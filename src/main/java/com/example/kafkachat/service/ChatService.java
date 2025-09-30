/*
package com.example.kafkachat.service;

import com.example.kafkachat.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class ChatService {
    @Autowired private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired private UserService userService;

    private static final List<String> GROUP1_ROLES = Arrays.asList("MODERATEUR", "SOURCING", "COMMERCE", "ADV");
    private static final List<String> GROUP2_ROLES = Arrays.asList("CLIENT", "CANDIDAT", "AMBASSADEUR", "SOURCING_COMMERCE", "AUTRE");

    public void sendMessage(String fromUsername, String toUsername, String message) {
        User fromUser = userService.findByUsername(fromUsername).orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        User toUser = userService.findByUsername(toUsername).orElseThrow(() -> new IllegalArgumentException("Recipient not found"));

        String topic = getAllowedTopic(fromUser, toUser);
        if (topic == null) {
            throw new IllegalArgumentException("Chat not allowed between these roles!");
        }

        String chatMessage = fromUsername + ": " + message;
        kafkaTemplate.send(topic, fromUsername, chatMessage);
    }

    public List<String> getAllowedRecipients(String username) {
        User user = userService.findByUsername(username).orElseThrow();
        String role = user.getRole();
        List<User> allowedUsers;

        if ("ADMIN".equals(role)) {
            allowedUsers = userService.findAll().stream()
                    .filter(u -> GROUP1_ROLES.contains(u.getRole()))
                    .toList();
        } else if (GROUP1_ROLES.contains(role)) {
            allowedUsers = userService.findAll().stream()
                    .filter(u -> !u.getUsername().equals(username) &&
                            (GROUP1_ROLES.contains(u.getRole()) || GROUP2_ROLES.contains(u.getRole()) || "ADMIN".equals(u.getRole())))
                    .toList();
        } else if (GROUP2_ROLES.contains(role)) {
            allowedUsers = userService.findAll().stream()
                    .filter(u -> GROUP1_ROLES.contains(u.getRole()))
                    .toList();
        } else {
            return List.of();
        }

        return allowedUsers.stream().map(User::getUsername).toList();
    }

    public String getChatTopic(String fromUsername, String toUsername) {
        User fromUser = userService.findByUsername(fromUsername).orElseThrow();
        User toUser = userService.findByUsername(toUsername).orElseThrow();
        return getAllowedTopic(fromUser, toUser);
    }

    private String getAllowedTopic(User from, User to) {
        String fromRole = from.getRole();
        String toRole = to.getRole();

        if (GROUP1_ROLES.contains(fromRole) && GROUP1_ROLES.contains(toRole)) {
            return "private-" + Math.min(from.getId(), to.getId()) + "-" + Math.max(from.getId(), to.getId());
        }
        if ((GROUP1_ROLES.contains(fromRole) && GROUP2_ROLES.contains(toRole)) ||
                (GROUP2_ROLES.contains(fromRole) && GROUP1_ROLES.contains(toRole))) {
            return "private-" + Math.min(from.getId(), to.getId()) + "-" + Math.max(from.getId(), to.getId());
        }
        if ((GROUP1_ROLES.contains(fromRole) && "ADMIN".equals(toRole)) ||
                ("ADMIN".equals(fromRole) && GROUP1_ROLES.contains(toRole))) {
            return "private-admin-" + Math.min(from.getId(), to.getId()) + "-" + Math.max(from.getId(), to.getId());
        }
        return null;
    }

    public boolean isGroup1(String role) { return GROUP1_ROLES.contains(role); }
    public boolean isGroup2(String role) { return GROUP2_ROLES.contains(role); }
}
*/
