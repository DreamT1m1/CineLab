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

    Optional<User> findUserById(Long id);

    boolean existsByUsername(String userName);

    @Query("UPDATE WatchedMovie wm SET wm.rating = :rating WHERE wm.user.id = :userId AND wm.movieId = :movieId")
    @Transactional
    @Modifying
    void changeMovieRatingOfUser(@Param("movieId") Long movieId,
                                 @Param("userId") Long userId,
                                 @Param("rating") Integer rating);

    @Query("UPDATE WatchedMovie wm SET wm.review = :review WHERE wm.movieId = :movieId AND wm.user.id = :userId")
    @Transactional
    @Modifying
    void addReviewForMovieByUser(@Param("review") String review,
                                 @Param("movieId") Long movieId,
                                 @Param("userId") Long userId);

    @Query("SELECT wm.rating from WatchedMovie wm WHERE wm.movieId = :movieId AND wm.user.id = :userId")
    Integer getMovieRatingOfUser(@Param("movieId") Long movieId,
                                 @Param("userId") Long userId);

    @Query("UPDATE User user SET user.avatar = :avatar WHERE user.id = :userId")
    @Transactional
    @Modifying
    void updateAvatarForUser(@Param("avatar") String avatar, @Param("userId") Long userId);


}
