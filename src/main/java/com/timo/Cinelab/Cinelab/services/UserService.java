package com.timo.Cinelab.Cinelab.services;

import com.timo.Cinelab.Cinelab.models.User.CustomUserDetails;
import com.timo.Cinelab.Cinelab.models.User.User;
import com.timo.Cinelab.Cinelab.models.movie.WatchedMovie;
import com.timo.Cinelab.Cinelab.repository.FriendInviteRepository;
import com.timo.Cinelab.Cinelab.repository.FriendRelationRepository;
import com.timo.Cinelab.Cinelab.repository.UserRepository;
import com.timo.Cinelab.Cinelab.repository.WatchedMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final WatchedMovieRepository watchedMovieRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       WatchedMovieRepository watchedMovieRepository,
                       FriendRelationRepository friendRelationRepository,
                       FriendInviteRepository friendInviteRepository) {

        this.userRepository = userRepository;
        this.watchedMovieRepository = watchedMovieRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        return userDetails.getUser();
    }

    public User getUserByUsername(String userName) {
        return userRepository.
                findUserByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with username %s was not found", userName)));
    }

    public User getUserById(Long userId) {
        return userRepository.
                findUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with provided id was not found"));
    }

    public void userWatchedMovie(WatchedMovie watchedMovie) {
        watchedMovieRepository.save(watchedMovie);
    };

    public void userNotWatchedMovie(long watchedMovieId,
                                    Long userId) {
        watchedMovieRepository.removeWatchedMovieByUserIdAndMovieId(userId, watchedMovieId);
    }

    public boolean hasUserWatchedMovie(Long userId, long watchedMovieId) {
        return watchedMovieRepository.getWatchedMovieByUserIdAndMovieId(userId, watchedMovieId).isPresent();
    }

    public List<WatchedMovie> getUserWatchedMovies(Long userId) {
        return watchedMovieRepository.getWatchedMovieByUserId(userId);
    }

    public Page<WatchedMovie> getUserWatchedMovies(Long userId, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("title").ascending());
        return watchedMovieRepository.findByUserId(userId, pageable);
    }

    public void addRatingInProfile(long movieId, Long userId, Integer rating) {
        userRepository.changeMovieRatingOfUser(movieId, userId, rating);
    }

    public void addReviewForMovie(Long movieId, Long userId, String review) {
        userRepository.addReviewForMovieByUser(review, movieId, userId);
    }

    public Integer getMovieRatingOfUser(long movieId, Long userId) {
        return userRepository.getMovieRatingOfUser(movieId, userId);
    }

    public void setAvatarForUser(String avatar, Long userId) {
        userRepository.updateAvatarForUser(avatar, userId);
    }

    public boolean userExistsByUsername(String userName) {
        return userRepository.existsByUsername(userName);
    }
}
