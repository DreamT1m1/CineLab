package com.timo.Cinelab.Cinelab.models.chat;

import com.timo.Cinelab.Cinelab.models.User.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @Column(name = "text", nullable = false, length = 2000)
    private String text;

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt = LocalDateTime.now();

    @Column(name = "is_read")
    private boolean read = false;


    public Message(Conversation conversation, User sender, String text) {
        this.conversation = conversation;
        this.sender = sender;
        this.text = text;
    }

    public Message() {}
}
