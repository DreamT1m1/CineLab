package com.timo.Cinelab.Cinelab.controllers;

import com.timo.Cinelab.Cinelab.models.User.CustomUserDetails;
import com.timo.Cinelab.Cinelab.models.User.User;
import com.timo.Cinelab.Cinelab.services.FriendService;
import com.timo.Cinelab.Cinelab.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.AccessDeniedException;

@Controller
public class FriendController {

    private final UserService userService;
    private final FriendService friendService;

    @Autowired
    public FriendController(UserService userService, FriendService friendService) {
        this.userService = userService;
        this.friendService = friendService;
    }

    @GetMapping("/{username}/friend_invites")
    public String getFriendInvitesPage(@PathVariable(name = "username") String userName,
                                       Model model,
                                       @AuthenticationPrincipal CustomUserDetails userDetails) {

        User currentUser = userDetails.getUser();

        if (!userName.equals(currentUser.getUsername())) {
            return "error_pages/error_page";
        }

        model.addAttribute("received_invites", friendService.getIncomingInvites(currentUser));

        return "private/userRelatedPages/friend_invites_page";
    }

    @GetMapping("/{username}/friends")
    public String getFriendsPages(@PathVariable(name = "username") String userName,
                                  Model model,
                                  @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (!userName.equals(userDetails.getUser().getUsername())) {
            return "error_pages/error_page";
        }

        User currentUser = userDetails.getUser();

        model.addAttribute("friendsList", friendService.getFriendsOfUser(currentUser));

        return "private/userRelatedPages/friends";
    }

    @PostMapping("/{username}/send_friend_invite")
    @ResponseBody
    public String sendFriendInvite(@PathVariable(name = "username") String userName) {

        User sender = userService.getCurrentUser();
        User receiver = userService.getUserByUsername(userName);

        friendService.sendRequest(sender, receiver);

        return "";
    }

    @PostMapping("/friend_invites/{id}/accept")
    @ResponseBody
    public void acceptInvite(@PathVariable Long id) throws AccessDeniedException {
        friendService.acceptInvite(id);
    }

    @PostMapping("/friend_invites/{id}/reject")
    @ResponseBody
    public void rejectInvite(@PathVariable Long id) {
        friendService.rejectInvite(id);
    }

    @PostMapping("/{username}/remove_friend/{id}")
    @ResponseBody
    public void removeFriend(@PathVariable(name = "username") String userName,
                             @PathVariable(name = "id") int friendId) {

    }
}
