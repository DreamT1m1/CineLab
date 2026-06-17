package com.timo.Cinelab.Cinelab.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/account")
    public String getAccountPage() {
        return "private/userRelatedPages/account";
    }
}
