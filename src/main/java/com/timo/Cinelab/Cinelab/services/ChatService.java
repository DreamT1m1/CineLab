package com.timo.Cinelab.Cinelab.services;

import com.timo.Cinelab.Cinelab.models.User.User;
import com.timo.Cinelab.Cinelab.models.chat.Conversation;
import com.timo.Cinelab.Cinelab.models.chat.Message;
import com.timo.Cinelab.Cinelab.models.chat.SendMessage;
import com.timo.Cinelab.Cinelab.repository.ConversationRepository;
import com.timo.Cinelab.Cinelab.repository.FriendRelationRepository;
import com.timo.Cinelab.Cinelab.repository.MessageRepository;
import com.timo.Cinelab.Cinelab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final FriendRelationRepository friendRelationRepository;

    @Autowired
    public ChatService(ConversationRepository conversationRepository, MessageRepository messageRepository, UserRepository userRepository, FriendRelationRepository friendRelationRepository) {
        this.conversationRepository = conversationRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.friendRelationRepository = friendRelationRepository;
    }

    private Conversation getOrCreateConversation(User user1, User user2) {

        if (user1.getId() > user2.getId()) {
            User temp = user1;
            user1 = user2;
            user2 = temp;
        }
        User finalUser1 = user1;
        User finalUser2 = user2;
        return conversationRepository.findByFirstUserAndSecondUser(user1, user2)
                .orElseGet(() -> {
                    Conversation conversation = new Conversation();

                    conversation.setFirstUser(finalUser1);
                    conversation.setSecondUser(finalUser2);

                    return conversationRepository.save(conversation);
                });
    }

    public Message sendMessage(Long senderId, SendMessage sendMessage) {

        User sender = userRepository.findUserById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepository.findUserById(sendMessage.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Conversation conversation = getOrCreateConversation(
                sender,
                receiver
        );

        Message message = new Message(conversation, sender, sendMessage.getText());

        return messageRepository.save(message);
    }
}
