package com.timo.Cinelab.Cinelab.services;

import com.timo.Cinelab.Cinelab.models.User.User;
import com.timo.Cinelab.Cinelab.models.movie.WatchedMovie;
import com.timo.Cinelab.Cinelab.repository.UserRepository;
import com.timo.Cinelab.Cinelab.repository.WatchedMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserService(UserRepository userRepository, WatchedMovieRepository watchedMovieRepository) {
        this.userRepository = userRepository;
        this.watchedMovieRepository = watchedMovieRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository
                .findUserByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with email %s was not found", email)));
    }

    public User getUserByUsername(String userName) {
        return userRepository.
                findUserByUsername(userName)
                .orElseThrow(() -> new IllegalArgumentException(String.format("User with username %s was not found", userName)));
    }

    public void userWatchedMovie(WatchedMovie watchedMovie) {
        watchedMovieRepository.save(watchedMovie);
    };

    public void userNotWatchedMovie(long watchedMovieId,
                                    int userId) {
        watchedMovieRepository.removeWatchedMovieByUserIdAndMovieId(userId, watchedMovieId);
    }

    public boolean hasUserWatchedMovie(int userId, long watchedMovieId) {
        return watchedMovieRepository.getWatchedMovieByUserIdAndMovieId(userId, watchedMovieId).isPresent();
    }

    public List<WatchedMovie> getUserWatchedMovies(int userId) {
        return watchedMovieRepository.getWatchedMovieByUserId(userId);
    }

    public void addRatingInProfile(long movieId, int userId, Integer rating) {
        userRepository.changeMovieRatingOfUser(movieId, userId, rating);
    }

    public Integer getMovieRatingOfUser(long movieId, int userId) {
        return userRepository.getMovieRatingOfUser(movieId, userId);
    }

    public void setAvatarForUser(String avatar, int userId) {
        userRepository.updateAvatarForUser(avatar, userId);
    }

    public boolean userExistsByUsername(String userName) {
        return userRepository.existsByUsername(userName);
    }
}
