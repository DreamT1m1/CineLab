package com.timo.Cinelab.Cinelab.repository;

import com.timo.Cinelab.Cinelab.models.User.User;
import com.timo.Cinelab.Cinelab.models.chat.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    @Query("""
            SELECT c
            FROM Conversation c
            WHERE
            (c.firstUser = :user1 AND c.secondUser = :user2)
            OR
            (c.firstUser = :user2 AND c.secondUser = :user1)
            """)
    Optional<Conversation> findByFirstUserAndSecondUser(User user1, User user2);
}
