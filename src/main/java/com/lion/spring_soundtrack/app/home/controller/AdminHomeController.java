package com.lion.spring_soundtrack.app.home.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {
    @GetMapping("")
    public String showIndex() {
        return "redirect:/admin/home/main";
    }

    @GetMapping("/home/main")
    public String showMain() {
        return "admin/home/main";
    }
}
