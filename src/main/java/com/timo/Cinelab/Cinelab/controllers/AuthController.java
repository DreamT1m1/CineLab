package com.timo.Cinelab.Cinelab.controllers;

import com.timo.Cinelab.Cinelab.models.User.User;
import com.timo.Cinelab.Cinelab.models.User.UserRole;
import com.timo.Cinelab.Cinelab.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "public/Auth/login_page";
    }

    @GetMapping("/register")
    public String getRegistrationPage() {
        return "public/Auth/registration_page";
    }

    @PostMapping("/register")
    public String createNewUser(@RequestParam String username,
                                @RequestParam String email,
                                @RequestParam String name,
                                @RequestParam String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(username, email, name, encodedPassword, UserRole.USER);
        userService.save(newUser);
        return "redirect:/login";
    }
}
