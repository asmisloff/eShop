package ru.asmisloff.eshop.shopadminui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.asmisloff.eshop.shopdatabase.entities.Authority;
import ru.asmisloff.eshop.shopdatabase.entities.User;
import ru.asmisloff.eshop.shopdatabase.services.AuthorityService;
import ru.asmisloff.eshop.shopdatabase.services.UserService;

import javax.validation.Valid;

@Controller
public class MainController {

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("activePage", "None");
        return "index";
    }


}
