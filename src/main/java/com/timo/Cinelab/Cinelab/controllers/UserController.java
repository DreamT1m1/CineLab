package com.timo.Cinelab.Cinelab.controllers;

import com.timo.Cinelab.Cinelab.models.User.CustomUserDetails;
import com.timo.Cinelab.Cinelab.models.User.User;
import com.timo.Cinelab.Cinelab.models.movie.WatchedMovie;
import com.timo.Cinelab.Cinelab.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public String getAccountPage(@PathVariable(name = "username") String username,
                                 Model model,
                                 @AuthenticationPrincipal CustomUserDetails userDetails) {

        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        model.addAttribute("userWatchedMovies", userService.getUserWatchedMovies(user.getId()));
        if (userDetails == null) {
            model.addAttribute("isCorrectUser", false);
        } else {
            model.addAttribute("isCorrectUser", userDetails.getUser().getUsername().equals(username));
        }

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

    @PostMapping("/add_rating_in_profile")
    @ResponseBody
    public String addRatingInProfile(@RequestParam Long movieId,
                                     @RequestParam int userId,
                                     @RequestParam(required = false) Integer rating) {
        User user = userService.getCurrentUser();
        if (userId == user.getId()) {
            System.out.println(movieId + " " + userId + " " + rating);
            userService.addRatingInProfile(movieId, userId, rating);
        }

        return "";
    }

    @PostMapping("/set_avatar")
    public String setAvatarForUser(@RequestParam MultipartFile avatar,
                                   Authentication authentication) throws IOException {

        if (!avatar.getContentType().startsWith("image/")) {
            throw new IllegalArgumentException(("Only images allowed"));
        } else {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            User user = userService.getUserByUsername(userDetails.getUser().getUsername());
            String oldAvatar = user.getAvatar();
            Path avatarDirectory = Paths.get("uploads/avatars");

            if (oldAvatar != null && !oldAvatar.isEmpty()) {
                Path oldAvatarPath = avatarDirectory.resolve(oldAvatar);

                System.out.println(oldAvatarPath);

                if (Files.exists(oldAvatarPath)) {
                    System.out.println(true);
                    Files.delete(oldAvatarPath);
                }
            }

            String fileName = UUID.randomUUID() + "_" + avatar.getOriginalFilename();

            Files.createDirectories(avatarDirectory);

            avatar.transferTo(avatarDirectory.resolve(fileName));

            userService.setAvatarForUser(fileName, user.getId());

            return String.format("redirect:/%s", user.getUsername());
        }
    }

    @GetMapping("/{username}/friend_invites")
    public String getFriendInvitesPage(@PathVariable(name = "username") String userName,
                                       Model model,
                                       @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (!userName.equals(userDetails.getUser().getUsername())) {
            return "error_pages/error_page";
        }

        return "private/userRelatedPages/friend_invites_page";
    }

    @GetMapping("/{username}/friends")
    public String getFriendsPages(@PathVariable(name = "username") String userName,
                                  Model model,
                                  @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (!userName.equals(userDetails.getUser().getUsername())) {
            return "error_pages/error_page";
        }

        return "private/userRelatedPages/friends";
    }
}
