package com.timo.Cinelab.Cinelab.repository;

import com.timo.Cinelab.Cinelab.models.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmailIgnoreCase(String email);

    Optional<User> findUserByUsername(String username);

    @Query("UPDATE WatchedMovie wm SET wm.rating = :rating where wm.user.id = :userId and wm.movieId = :movieId")
    @Transactional
    @Modifying
    void changeMovieRatingOfUser(@Param("movieId") Long movieId,
                                 @Param("userId") Integer userId,
                                 @Param("rating") Integer rating);

    @Query("SELECT wm.rating from WatchedMovie wm WHERE wm.movieId = :movieId AND wm.user.id = :userId")
    Integer getMovieRatingOfUser(@Param("movieId") Long movieId,
                                 @Param("userId") int userId);

    @Query("UPDATE User user SET user.avatar = :avatar WHERE user.id = :userId")
    @Transactional
    @Modifying
    void updateAvatarForUser(@Param("avatar") String avatar, @Param("userId") int userId);
}
