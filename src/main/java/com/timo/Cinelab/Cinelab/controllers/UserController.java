package com.timo.Cinelab.Cinelab.controllers;

import com.timo.Cinelab.Cinelab.models.User.User;
import com.timo.Cinelab.Cinelab.models.movie.WatchedMovie;
import com.timo.Cinelab.Cinelab.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public String getAccountPage(@PathVariable String username, Model model) {
        model.addAttribute("user", userService.getUserByUsername(username));
        return "private/userRelatedPages/account";
    }

    @PostMapping("/change_movie_status")
    @ResponseBody
    public String changeMovieStatusForUser(@RequestParam Long movieId,
                                                   @RequestParam String status,
                                                   @RequestParam String movieTitle) {
        User user = userService.getCurrentUser();
        WatchedMovie watchedMovie = new WatchedMovie(user, movieId, movieTitle);
        if (status.equals("watched")) {;
            userService.userWatchedMovie(watchedMovie);
        } else if (status.equals("not_watched")) {
            userService.userNotWatchedMovie(watchedMovie.getMovieId(), user.getId());
        }
        return "";
    }
}
