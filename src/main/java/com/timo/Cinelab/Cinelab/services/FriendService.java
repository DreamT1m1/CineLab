package com.timo.Cinelab.Cinelab.services;

import com.timo.Cinelab.Cinelab.models.User.FriendInvite;
import com.timo.Cinelab.Cinelab.models.User.FriendRelation;
import com.timo.Cinelab.Cinelab.models.User.FriendState;
import com.timo.Cinelab.Cinelab.models.User.User;
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

    @Autowired
    public FriendService(FriendRelationRepository friendRelationRepository, FriendInviteRepository friendInviteRepository, UserService userService) {
        this.friendRelationRepository = friendRelationRepository;
        this.friendInviteRepository = friendInviteRepository;
        this.userService = userService;
    }

    public List<User> getFriendsOfUser(User user) {
        return friendRelationRepository.findAllByUser(user).stream().map(FriendRelation::getFriend).toList();
    }

    public void sendRequest(User sender, User receiver) {
        friendInviteRepository.save(new FriendInvite(sender, receiver));
    }

    public boolean friendInviteExists(User sender, User receiver) {
        return friendInviteRepository.existsBySenderAndReceiver(sender, receiver);
    }

    public List<FriendInvite> getIncomingInvites(User user) {
        return friendInviteRepository.findAllByReceiver(user);
    }

    @Transactional
    public void acceptInvite(Long inviteId) throws AccessDeniedException {

        FriendInvite invite = friendInviteRepository.findById(inviteId).orElseThrow();

        User sender = invite.getSender();
        User receiver = invite.getReceiver();

        if (!receiver.equals(userService.getCurrentUser())) {
            throw new AccessDeniedException("Forbidden");
        }

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
}
