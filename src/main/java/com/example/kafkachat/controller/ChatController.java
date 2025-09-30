package com.example.kafkachat.controller;

import com.example.kafkachat.Models.User;
import com.example.kafkachat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private UserService userService;

    @GetMapping("/check")
    public ResponseEntity<Map<String, Boolean>> checkChatAllowed(
            @RequestParam String sender,
            @RequestParam String receiver) {

        User senderUser = userService.getUserByUsername(sender);
        User receiverUser = userService.getUserByUsername(receiver);

        boolean allowed = isChatAllowed(senderUser.getRole(), receiverUser.getRole());

        return ResponseEntity.ok(Map.of("allowed", allowed));
    }

    private boolean isChatAllowed(String roleA, String roleB) {
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
