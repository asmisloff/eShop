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
import java.util.List;

@Controller
public class MainController {

    private UserService userService;
    private AuthorityService authorityService;

    public MainController(UserService userService, AuthorityService authorityService) {
        this.userService = userService;
        this.authorityService = authorityService;
    }

    @GetMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("activePage", "None");
        model.addAttribute("users", userService.findAll());
        model.addAttribute("authorities", authorityService.findAll());
        return "index";
    }

    @GetMapping("users")
    public String showUsersList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("add_user")
    public String showAddUserForm(Model model) {
        model
                .addAttribute("authorities", authorityService.findAll())
                .addAttribute("user", new User());

        return "add_user";
    }

    @GetMapping("edit_user")
    public String showEditUserForm(
            Model model,
            @RequestParam("id") Long id) {
        model.addAttribute("user_id", id);
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("authorities", authorityService.findAll());
        return "edit_user";
    }

    //todo: validation
    @PostMapping("add_user")
    public String addUser(
            @Valid @ModelAttribute User user,
            @RequestParam(value = "authorityIdsChecked", required = false) List<Long> authorityIds,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "add_user";
        }

        if (authorityIds != null) {
            authorityIds.forEach(authorityId -> user.addAuthority(authorityService.findById(authorityId)));
        }
        user.setId(null);
        userService.save(user);
        return "redirect:/";
    }

    //todo: validation
    @PostMapping("edit_user")
    public String editUser(
            @Valid @ModelAttribute User user,
            @RequestParam(value = "authorityIdsChecked", required = false) List<Long> authorityIds,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return "edit_user";
        }

        if (authorityIds != null) {
            authorityIds.forEach(authorityId -> user.addAuthority(authorityService.findById(authorityId)));
        }

        userService.update(user);
        return "redirect:/";
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

    //todo: validation
    @PostMapping("add_authority")
    public String addAuthority(@Valid @ModelAttribute Authority authority) {
        authority.setId(null);
        authorityService.save(authority);
        return "redirect:/";
    }

    //todo: validation
    @PostMapping("edit_authority")
    public String editAuthority(@Valid @ModelAttribute Authority authority, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit_authority";
        }
        authorityService.save(authority);
        return "redirect:/";
    }

    @GetMapping("/delete_user")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/delete_authority")
    public String deleteAuthority(@RequestParam("id") Long id) {
        authorityService.deleteById(id);
        return "redirect:/";
    }

}
