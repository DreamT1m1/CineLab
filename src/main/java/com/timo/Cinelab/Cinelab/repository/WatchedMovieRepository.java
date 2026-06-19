package com.timo.Cinelab.Cinelab.repository;

import com.timo.Cinelab.Cinelab.models.movie.WatchedMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WatchedMovieRepository extends JpaRepository<WatchedMovie, Integer> {
    void removeWatchedMoviesByUserIdAndMovieId(int userId, long movieId);
    Optional<WatchedMovie> getWatchedMovieByUserIdAndMovieId(int userId, long movieId);
}
