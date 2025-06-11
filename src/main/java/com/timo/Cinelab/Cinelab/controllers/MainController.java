package com.timo.Cinelab.Cinelab.controllers;

import com.timo.Cinelab.Cinelab.movieapi.MovieApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/movies")
public class MainController {

    private final MovieApi movieApi;

    @Autowired
    public MainController(MovieApi movieApi) {
        this.movieApi = movieApi;
    }

    @GetMapping
    public String getMoviesPage(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                               @RequestParam(value = "title", required = false) String title,
                               Model model) {
        if (page != null) {
            model.addAttribute("moviesList", movieApi.getMoviePage(page));
        }
        if (title != null) {
            model.addAttribute("moviesList", movieApi.getMoviesByTitle(title));
        }
        return "movies/movies_page";
    }

    @GetMapping("/{id}")
    public String getMovieById(@PathVariable("id") long id, Model model) {
        model.addAttribute("movie", movieApi.getMovieById(id));
        return "movies/movie_page";
    }

}
