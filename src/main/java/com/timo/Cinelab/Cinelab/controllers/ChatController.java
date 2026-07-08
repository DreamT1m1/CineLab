package com.timo.Cinelab.Cinelab.controllers;

import com.timo.Cinelab.Cinelab.models.User.User;
import com.timo.Cinelab.Cinelab.models.chat.Message;
import com.timo.Cinelab.Cinelab.models.chat.SendMessage;
import com.timo.Cinelab.Cinelab.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat/{userId}")
    public String chatPage(
            @PathVariable Long userId,
            Model model) {

        model.addAttribute("receiverId", userId);

        return "chatting/chatPage";
    }
}
