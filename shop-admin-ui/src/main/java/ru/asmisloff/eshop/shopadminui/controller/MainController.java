package ru.asmisloff.eshop.shopadminui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("user", "None");
        return "index";
    }


}
