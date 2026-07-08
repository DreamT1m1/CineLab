package com.timo.Cinelab.Cinelab.repository;

import com.timo.Cinelab.Cinelab.models.chat.Conversation;
import com.timo.Cinelab.Cinelab.models.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByConversationOrderBySentAtAsc(Conversation conversation);

    @Query("""
            SELECT m FROM Message m
            WHERE m.conversation = :conversation
            ORDER BY m.sentAt ASC
            """)
    List<Message> findConversationMessages(@Param("conversation") Conversation conversation);
}
