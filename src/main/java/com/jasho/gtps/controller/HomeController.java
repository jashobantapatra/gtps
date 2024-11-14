package com.jasho.gtps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Jashobanta Patra
 * crated on 11-11-2024
 */

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String homePage() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
