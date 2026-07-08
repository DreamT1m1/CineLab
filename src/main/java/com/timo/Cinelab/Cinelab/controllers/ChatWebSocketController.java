package com.timo.Cinelab.Cinelab.controllers;

import com.timo.Cinelab.Cinelab.models.User.CustomUserDetails;
import com.timo.Cinelab.Cinelab.models.User.User;
import com.timo.Cinelab.Cinelab.models.chat.Message;
import com.timo.Cinelab.Cinelab.models.chat.MessageResponse;
import com.timo.Cinelab.Cinelab.models.chat.SendMessage;
import com.timo.Cinelab.Cinelab.services.ChatService;
import com.timo.Cinelab.Cinelab.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatWebSocketController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserService userService;

    @Autowired
    public ChatWebSocketController(ChatService chatService, SimpMessagingTemplate messagingTemplate, UserService userService) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
        this.userService = userService;
    }

    @MessageMapping("/chat.send")
    public void sendMessage(SendMessage message, Principal principal) {

        CustomUserDetails userDetails =
                (CustomUserDetails) ((Authentication) principal)
                        .getPrincipal();

        User sender = userDetails.getUser();
        User receiver = userService.getUserById(message.getReceiverId());

        Message savedMessage = chatService.sendMessage(sender.getId(), message);
        MessageResponse response = new MessageResponse(sender.getId(), message.getText());

        messagingTemplate.convertAndSendToUser(
                receiver.getUsername(),
                "/queue/messages",
                response
        );

        messagingTemplate.convertAndSendToUser(
                sender.getUsername(),
                "/queue/messages",
                response
        );
    }
}
