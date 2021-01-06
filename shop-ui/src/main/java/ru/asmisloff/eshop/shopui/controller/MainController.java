package ru.asmisloff.eshop.shopui.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.asmisloff.eshop.shopdatabase.entities.Category;
import ru.asmisloff.eshop.shopdatabase.repositories.CategoryRepository;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private CategoryRepository categoryRepository;

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @RequestMapping("/")
    public String index(Model model) {
        List<Category> categories = categoryRepository.findAll();
        logger.info(String.valueOf(categories));
        model.addAttribute("categories", categories);
        model.addAttribute("activePage", "index");
        return "index";
    }

}
