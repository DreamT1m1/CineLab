package com.timo.Cinelab.Cinelab.controllers;

import com.timo.Cinelab.Cinelab.services.MoviesService;
import com.timo.Cinelab.Cinelab.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/movies")
public class MoviesController {

    private final MoviesService moviesService;
    private final UserService userService;

    @Autowired
    public MoviesController(MoviesService moviesService, UserService userService) {
        this.moviesService = moviesService;
        this.userService = userService;
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
        return "public/movies/movies_page";
    }

    @GetMapping("/{id}")
    public String getMovieById(@PathVariable("id") long id, Model model, Authentication authentication) {
        model.addAttribute("movie", moviesService.getMovieLargeById(id));
        model.addAttribute("mainBackdrops", moviesService.getMovieBackDrops(id));
        model.addAttribute("trailer", moviesService.getMovieTrailerUrl(id));
        model.addAttribute("videos", moviesService.getMostPopularMovieVideos(id));
        model.addAttribute("director", moviesService.getMovieDirector(id));
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("has_watched", userService.hasUserWatchedMovie(userService.getCurrentUser().getId(), id));
        }
        return "public/movies/movie_page";
    }

}
