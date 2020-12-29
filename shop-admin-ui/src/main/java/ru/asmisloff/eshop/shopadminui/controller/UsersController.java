package ru.asmisloff.eshop.shopadminui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.asmisloff.eshop.shopdatabase.entities.User;
import ru.asmisloff.eshop.shopadminui.services.UserService;

import javax.validation.Valid;

@Controller
public class UsersController {

    private UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("users")
    public String showUsersList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("add_user")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());

        return "add_user";
    }

    @GetMapping("edit_user")
    public String showEditUserForm(
            Model model,
            @RequestParam("id") Long id) {
        model.addAttribute("user_id", id);
        model.addAttribute("user", userService.findById(id));
        return "edit_user";
    }

    @PostMapping("add_user")
    public String addUser(
            @Valid @ModelAttribute User user,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "add_user";
        }

        user.setId(null);
        userService.save(user);
        return "redirect:/users";
    }

    @PostMapping("edit_user")
    public String editUser(
            @Valid @ModelAttribute User user,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return "edit_user";
        }

        userService.update(user);
        return "redirect:/users";
    }


    @GetMapping("/delete_user")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }

}
