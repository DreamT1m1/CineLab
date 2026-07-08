package com.timo.Cinelab.Cinelab.repository;

import com.timo.Cinelab.Cinelab.models.User.User;
import com.timo.Cinelab.Cinelab.models.chat.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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
    Optional<Conversation> findByFirstUserAndSecondUser(@Param("user1") User user1,
                                                        @Param("user2") User user2);

    @Query("""
            SELECT c
            FROM Conversation c
            WHERE
            c.firstUser = :user OR
            c.secondUser = :user
            """)
    List<Conversation> findAllUserChats(@Param("user") User user);
}
