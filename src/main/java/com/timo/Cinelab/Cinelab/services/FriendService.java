package com.timo.Cinelab.Cinelab.services;

import com.timo.Cinelab.Cinelab.models.User.*;
import com.timo.Cinelab.Cinelab.models.webSocket.Notification;
import com.timo.Cinelab.Cinelab.repository.FriendInviteRepository;
import com.timo.Cinelab.Cinelab.repository.FriendRelationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Optional;

@Service
public class FriendService {

    private final FriendRelationRepository friendRelationRepository;
    private final FriendInviteRepository friendInviteRepository;
    private final UserService userService;
    private final NotificationService notificationService;

    @Autowired
    public FriendService(FriendRelationRepository friendRelationRepository, FriendInviteRepository friendInviteRepository, UserService userService, NotificationService notificationService) {
        this.friendRelationRepository = friendRelationRepository;
        this.friendInviteRepository = friendInviteRepository;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    public List<User> getFriendsOfUser(User user) {
        return friendRelationRepository.findAllByUser(user).stream().map(FriendRelation::getFriend).toList();
    }

    public void sendRequest(User sender, User receiver) {
        FriendInvite friendInvite = friendInviteRepository.save(new FriendInvite(sender, receiver));

        notificationService.sendNotification(
                receiver.getUsername(),
                new FriendEvent("FRIEND_REQUEST", sender.getUsername(), friendInvite.getId())
        );
    }

    public boolean friendInviteExists(User sender, User receiver) {
        return friendInviteRepository.existsBySenderAndReceiver(sender, receiver);
    }

    public List<FriendInvite> getIncomingInvites(User user) {
        return friendInviteRepository.findAllByReceiver(user);
    }

    @Transactional
    public void acceptInvite(Long inviteId) {

        FriendInvite invite = friendInviteRepository.findById(inviteId).orElseThrow();

        User sender = invite.getSender();
        User receiver = invite.getReceiver();

        notificationService.sendNotification(
                sender.getUsername(),
                new FriendEvent("FRIEND_ACCEPTED", receiver.getUsername(), null)
        );

        notificationService.sendNotification(
                receiver.getUsername(),
                new FriendEvent("FRIEND_ACCEPTED", sender.getUsername(), null)
        );

        if (!sender.equals(receiver)) {
            FriendRelation friendRelation1 = new FriendRelation(sender, receiver);
            FriendRelation friendRelation2 = new FriendRelation(receiver, sender);

            friendRelationRepository.save(friendRelation1);
            friendRelationRepository.save(friendRelation2);

            friendInviteRepository.delete(invite);
        }
    }

    @Transactional
    public void rejectInvite(Long inviteId) {
        FriendInvite invite = friendInviteRepository.findById(inviteId).orElseThrow();

        User sender = invite.getSender();
        User receiver = invite.getReceiver();

        notificationService.sendNotification(
                sender.getUsername(),
                new FriendEvent("FRIEND_REJECTED", receiver.getUsername(), null)
        );

        notificationService.sendNotification(
                receiver.getUsername(),
                new FriendEvent("FRIEND_REJECTED", sender.getUsername(), null)
        );

        friendInviteRepository.delete(friendInviteRepository.findById(inviteId).orElseThrow());
    }

    public FriendState getFriendState(User currentUser, User profileUser) {

        if (friendRelationRepository.existsByUserAndFriend(currentUser, profileUser)) {
            return FriendState.FRIENDS;
        }
        if(friendInviteRepository.existsBySenderAndReceiver(currentUser, profileUser)) {
            return FriendState.REQUEST_SENT;
        }

        if(friendInviteRepository.existsBySenderAndReceiver(profileUser, currentUser)) {
            return FriendState.REQUEST_RECEIVED;
        }
        return FriendState.NONE;
    }

    public Optional<FriendInvite> getReceivedInvite(User currentUser, User profileUser) {
        return friendInviteRepository.findBySenderAndReceiver(profileUser, currentUser);
    }

    public void deleteFriendRelation(Long userId, Long friendId) {
        friendRelationRepository.deleteFriendRelation(userId, friendId);

        User user1 = userService.getUserById(userId);
        User user2 = userService.getUserById(friendId);

        notificationService.sendNotification(
                user1.getUsername(),
                new FriendEvent("DELETE_RELATION", user2.getUsername(), null)
        );

        notificationService.sendNotification(
                user2.getUsername(),
                new FriendEvent("DELETE_RELATION", user1.getUsername(), null)
        );
    }
}
