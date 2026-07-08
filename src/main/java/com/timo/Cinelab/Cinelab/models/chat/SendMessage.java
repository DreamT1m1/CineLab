package com.timo.Cinelab.Cinelab.models.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMessage {

    private Long receiverId;
    private String text;

    public SendMessage(Long receiverId, String text) {
        this.receiverId = receiverId;
        this.text = text;
    }
}
