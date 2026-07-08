package com.timo.Cinelab.Cinelab.models.chat;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MessageResponse {

    private Long senderId;
    private String text;
    private LocalDateTime sentAt = LocalDateTime.now();

    public MessageResponse(Long senderId, String text) {
        this.senderId = senderId;
        this.text = text;
    }
}
