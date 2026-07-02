package com.timo.Cinelab.Cinelab.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/hello")
    @SendTo("/topic/messages")
    public String hello(String message) {
        return "Server received: " + message;
    }


}
