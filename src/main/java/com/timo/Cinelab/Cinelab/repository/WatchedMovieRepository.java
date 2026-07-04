package com.timo.Cinelab.Cinelab.repository;

import com.timo.Cinelab.Cinelab.models.movie.WatchedMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WatchedMovieRepository extends JpaRepository<WatchedMovie, Integer> {
    void removeWatchedMovieByUserIdAndMovieId(Long userId, long movieId);
    Optional<WatchedMovie> getWatchedMovieByUserIdAndMovieId(Long userId, long movieId);
    List<WatchedMovie> getWatchedMovieByUserId(Long userId);
}
