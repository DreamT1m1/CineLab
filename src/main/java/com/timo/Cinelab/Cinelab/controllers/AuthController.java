package com.timo.Cinelab.Cinelab.controllers;

import com.timo.Cinelab.Cinelab.models.User.User;
import com.timo.Cinelab.Cinelab.models.User.UserRole;
import com.timo.Cinelab.Cinelab.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Set;

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
    public String getLoginPage(Model model, @RequestParam(required = false) String error) {
        if (error != null) {
            model.addAttribute("isAuthenticationFailed", true);
        }
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
        forceAutoLogin(email, encodedPassword);
        return "redirect:/account";
    }

    private void forceAutoLogin(String email, String password) {
        Set<SimpleGrantedAuthority> roles = Collections.singleton(UserRole.USER.toAuthority());
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password, roles);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
