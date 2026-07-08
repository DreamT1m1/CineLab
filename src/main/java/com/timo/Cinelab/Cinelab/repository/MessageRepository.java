package com.timo.Cinelab.Cinelab.repository;

import com.timo.Cinelab.Cinelab.models.chat.Conversation;
import com.timo.Cinelab.Cinelab.models.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByConversationOrderBySentAtAsc(Conversation conversation);
}
