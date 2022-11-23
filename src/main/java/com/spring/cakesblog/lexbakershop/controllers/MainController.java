package com.spring.cakesblog.lexbakershop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Главная страница");
        return "home-view1";
    }

    @GetMapping("/aboutme")
    public String aboutMe(Model model){
        return "about-me-view";
    }

    @GetMapping("/contacts")
    public String myContacts(Model model){
        return "my-contacts-view";
    }

}
