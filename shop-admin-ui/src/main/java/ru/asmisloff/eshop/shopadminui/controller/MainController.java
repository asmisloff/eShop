package ru.asmisloff.eshop.shopadminui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.asmisloff.eshop.shopadminui.services.AuthorityService;
import ru.asmisloff.eshop.shopadminui.services.UserService;

@Controller
public class MainController {

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("activePage", "None");
        return "index";
    }


}
