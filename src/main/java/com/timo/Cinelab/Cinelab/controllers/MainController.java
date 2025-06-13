package com.timo.Cinelab.Cinelab.controllers;

import com.timo.Cinelab.Cinelab.models.movie.BackDrop;
import com.timo.Cinelab.Cinelab.models.movie.MovieLarge;
import com.timo.Cinelab.Cinelab.movieapi.MovieApi;
import com.timo.Cinelab.Cinelab.services.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MainController {

    private final MoviesService moviesService;

    @Autowired
    public MainController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @GetMapping
    public String getMoviesPage(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                               @RequestParam(value = "title", required = false) String title,
                               Model model) {
        if (page != null) {
            model.addAttribute("moviesList", moviesService.getMoviePage(page));
        }
        if (title != null) {
            model.addAttribute("moviesList", moviesService.getMoviesByTitle(title));
        }
        return "movies/movies_page";
    }

    @GetMapping("/{id}")
    public String getMovieById(@PathVariable("id") long id, Model model) {
        model.addAttribute("movie", moviesService.getMovieLargeById(id));
        model.addAttribute("mainBackdrops", moviesService.getMovieBackDrops(id));
        model.addAttribute("trailer", moviesService.getMovieTrailerUrl(id));
        model.addAttribute("videos", moviesService.getMovieVideos(id));
        return "movies/movie_page";
    }

}
