package com.timo.Cinelab.Cinelab.controllers;

import com.timo.Cinelab.Cinelab.models.User.CustomUserDetails;
import com.timo.Cinelab.Cinelab.services.ChatService;
import com.timo.Cinelab.Cinelab.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final UserService userService;

    @Autowired
    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @GetMapping({"", "/{userId}"})
    public String chatPage(
            @PathVariable(required = false) Long userId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model) {

        if (userId != null) {
            model.addAttribute("receiver", userService.getUserById(userId));
        }
        model.addAttribute("chats", chatService.getAllUserConversations(userDetails.getUser()));

        return "chatting/chatPage";
    }

    @GetMapping("/{userId}/messages")
    @ResponseBody
    public ResponseEntity<?> getMessages(@PathVariable Long userId,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {

        return ResponseEntity.ok(
                chatService.getMessages(
                        userDetails.getUser().getId(),
                        userId
                )
        );
    }
}
