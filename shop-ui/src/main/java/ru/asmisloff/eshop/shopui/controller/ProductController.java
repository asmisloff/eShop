package ru.asmisloff.eshop.shopui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.asmisloff.eshop.common.error.NotFoundException;
import ru.asmisloff.eshop.common.repr.ProductRepr;
import ru.asmisloff.eshop.common.service.ProductService;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String showAllProducts(Model model) {
        List<ProductRepr> products = productService.findAll();
        model.addAttribute("products", products);
        return "product";
    }

    @GetMapping("{id}")
    public String showProductDetail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productService.findById(id).orElseThrow(NotFoundException::new));
        return "product_detail3";
    }

}
