package com.timo.Cinelab.Cinelab.models.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "friend_relation")
@Getter
@Setter
public class FriendRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id")
    private User friend;

    public FriendRelation(User user, User friend) {
        this.user = user;
        this.friend = friend;
    }

    public FriendRelation() {
    }
}
