package com.timo.Cinelab.Cinelab.repository;

import com.timo.Cinelab.Cinelab.models.User.FriendRelation;
import com.timo.Cinelab.Cinelab.models.User.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRelationRepository extends JpaRepository<FriendRelation, Long> {

    boolean existsByUserAndFriend(User user, User friend);

    List<FriendRelation> findAllByUser(User user);

    @Modifying
    @Transactional
    @Query("""
    DELETE FROM FriendRelation fr
    WHERE (fr.user.id = :userId AND fr.friend.id = :friendId)
       OR (fr.user.id = :friendId AND fr.friend.id = :userId)
    """)
    void deleteFriendRelation(Long userId, Long friendId);
}
