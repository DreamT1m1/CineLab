package com.timo.Cinelab.Cinelab.repository;

import com.timo.Cinelab.Cinelab.models.User.FriendInvite;
import com.timo.Cinelab.Cinelab.models.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendInviteRepository extends JpaRepository<FriendInvite, Long> {

    boolean existsBySenderAndReceiver(User sender, User receiver);

    Optional<FriendInvite> findBySenderAndReceiver(User sender, User receiver);

    List<FriendInvite> findAllByReceiver(User receiver);

    List<FriendInvite> findAllBySender(User sender);
}
