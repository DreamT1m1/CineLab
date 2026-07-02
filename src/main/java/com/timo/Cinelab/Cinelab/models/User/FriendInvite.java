package com.timo.Cinelab.Cinelab.models.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "friend_invite")
@Getter
@Setter
public class FriendInvite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver")
    private User receiver;

    public FriendInvite(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public FriendInvite() {
    }
}
