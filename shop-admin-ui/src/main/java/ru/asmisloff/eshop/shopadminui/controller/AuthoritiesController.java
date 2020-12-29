package ru.asmisloff.eshop.shopadminui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.asmisloff.eshop.shopdatabase.entities.Authority;
import ru.asmisloff.eshop.shopadminui.services.AuthorityService;

import javax.validation.Valid;

@Controller
public class AuthoritiesController {

    private AuthorityService authorityService;

    public AuthoritiesController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @GetMapping("/authorities")
    public String showAllAuthorities(Model model) {
        model.addAttribute("authorities", authorityService.findAll());
        return "authorities";
    }

    @GetMapping("add_authority")
    public String showAddAuthorityForm(Model model) {
        model.addAttribute(new Authority());
        return "add_authority";
    }

    @GetMapping("edit_authority")
    public String showEditAuthorityForm(Model model, @RequestParam("id") Long id) {
        model.addAttribute(authorityService.findById(id));
        return "edit_authority";
    }

    @PostMapping("add_authority")
    public String addAuthority(@Valid @ModelAttribute Authority authority, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "add_authority";
        }
        authority.setId(null);
        authorityService.save(authority);
        return "redirect:/authorities";
    }

    @PostMapping("edit_authority")
    public String editAuthority(@Valid @ModelAttribute Authority authority, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit_authority";
        }
        authorityService.save(authority);
        return "redirect:/authorities";
    }

    @GetMapping("/delete_authority")
    public String deleteAuthority(@RequestParam("id") Long id) {
        authorityService.deleteById(id);
        return "redirect:/authorities";
    }

}
