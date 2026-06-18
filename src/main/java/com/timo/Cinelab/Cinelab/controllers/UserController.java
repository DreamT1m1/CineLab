package com.timo.Cinelab.Cinelab.controllers;

import com.timo.Cinelab.Cinelab.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
}
