package com.timo.Cinelab.Cinelab.models.chat;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MessageResponse {

    private Long senderId;
    private String text;
    private LocalDateTime sentAt;

    public MessageResponse(Long senderId, String text, LocalDateTime sentAt) {
        this.senderId = senderId;
        this.text = text;
        this.sentAt = sentAt;
    }
}
