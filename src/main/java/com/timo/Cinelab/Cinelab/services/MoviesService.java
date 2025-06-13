package com.timo.Cinelab.Cinelab.services;

import com.timo.Cinelab.Cinelab.models.movie.BackDrop;
import com.timo.Cinelab.Cinelab.models.movie.Movie;
import com.timo.Cinelab.Cinelab.models.movie.MovieLarge;
import com.timo.Cinelab.Cinelab.models.movie.Video;
import com.timo.Cinelab.Cinelab.movieapi.MovieApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoviesService {

    private final MovieApi movieApi;

    @Autowired
    public MoviesService(MovieApi movieApi) {
        this.movieApi = movieApi;
    }

    public List<Movie> getMoviePage(int page) {
        return movieApi.getMoviePage(page);
    }

    public List<Movie> getMoviesByTitle(String title) {
        return movieApi.getMoviesByTitle(title);
    }

    public List<Movie> getPopularMovies() {
        return movieApi.getPopularMovies();
    }

    public MovieLarge getMovieLargeById(long id) {
        return movieApi.getMovieLargeById(id);
    }

    public List<BackDrop> getMovieBackDrops(long id) {
        return movieApi.getMovieBackDrops(id);
    }

    public List<Video> getMovieVideos(long id) {
        return movieApi.getMovieVideos(id)
                .stream()
                .filter(Video::getOfficial)
                .toList();
    }

    public String getMovieTrailerUrl(long id) {
        return movieApi.getMovieTrailerUrl(id);
    }
}
