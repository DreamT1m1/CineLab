package com.timo.Cinelab.Cinelab.controllers;

import com.timo.Cinelab.Cinelab.models.User.CustomUserDetails;
import com.timo.Cinelab.Cinelab.models.User.User;
import com.timo.Cinelab.Cinelab.models.movie.WatchedMovie;
import com.timo.Cinelab.Cinelab.services.FriendService;
import com.timo.Cinelab.Cinelab.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private final FriendService friendService;

    @Autowired
    public UserController(UserService userService, FriendService friendService) {
        this.userService = userService;
        this.friendService = friendService;
    }

    @GetMapping("/{username}")
    public String getAccountPage(@PathVariable(name = "username") String username,
                                 @RequestParam(defaultValue = "0") int page,
                                 Model model,
                                 @AuthenticationPrincipal CustomUserDetails userDetails) {

        User profileUser = userService.getUserByUsername(username);
        model.addAttribute("user", profileUser);

        Page<WatchedMovie> moviesPage = userService.getUserWatchedMovies(profileUser.getId(), page);

        model.addAttribute("moviesPage", moviesPage);
        model.addAttribute("userWatchedMovies", moviesPage.getContent());
        model.addAttribute("moviePagesEnough", moviesPage.getTotalPages() > 1);

        if (userDetails == null) {
            model.addAttribute("isCorrectUser", false);
            model.addAttribute("authenticated", false);
        } else {
            User currentUser = userDetails.getUser();
            boolean isCorrectUser = currentUser.getUsername().equals(username);
            model.addAttribute("authenticated", true);
            model.addAttribute("isCorrectUser", isCorrectUser);

            if (!isCorrectUser) {
                 model.addAttribute("friendState", friendService.getFriendState(currentUser, profileUser).name());
                 model.addAttribute("invite", friendService.getReceivedInvite(currentUser, profileUser).orElse(null));
            }
        }

        return "private/userRelatedPages/account";
    }

    @PostMapping("/change_movie_status")
    @ResponseBody
    public String changeMovieStatusForUser(@RequestParam Long movieId,
                                           @RequestParam String status,
                                           @RequestParam String movieTitle,
                                           @RequestParam Integer year
                                           ) {
        User user = userService.getCurrentUser();
        WatchedMovie watchedMovie = new WatchedMovie(user, movieId, movieTitle, year, "");
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
                                     @RequestParam Long userId,
                                     @RequestParam(required = false) Integer rating) {
        User user = userService.getCurrentUser();
        if (userId.equals(user.getId())) {
            userService.addRatingInProfile(movieId, userId, rating);
        }

        return "";
    }

    @PostMapping("/add_review")
    @ResponseBody
    public String addReviewForMovie(@RequestParam Long movieId,
                                    @RequestParam String review,
                                    @RequestParam String userName) {
        User user = userService.getCurrentUser();

        if (user.getUsername().equals(userName)) {
            userService.addReviewForMovie(movieId, user.getId(), review);

            return "";
        }

        return "redirect:/error_page";
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

                if (Files.exists(oldAvatarPath)) {
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

}
