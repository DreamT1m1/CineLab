package com.timo.Cinelab.Cinelab.repository;

import com.timo.Cinelab.Cinelab.models.User.FriendRelation;
import com.timo.Cinelab.Cinelab.models.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRelationRepository extends JpaRepository<FriendRelation, Long> {

    boolean existsByUserAndFriend(User user, User friend);

    List<FriendRelation> findAllByUser(User user);
}
