package ru.asmisloff.eshop.shopui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.asmisloff.eshop.common.error.NotFoundException;
import ru.asmisloff.eshop.common.repr.ProductRepr;
import ru.asmisloff.eshop.common.service.ProductService;
import ru.asmisloff.eshop.shopui.model.OrderEntry;
import ru.asmisloff.eshop.shopui.service.CartService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("orders", cartService.getOrders());
        return "cart";
    }

    @GetMapping("add")
    public void addProduct(
            @RequestParam("productId") Long productId, @RequestParam("qty") Integer qty,
            HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        ProductRepr productRepr = productService.findById(productId).orElseThrow(NotFoundException::new);
        cartService.add(productRepr, qty);
        response.sendRedirect(request.getHeader("referer"));
    }

    @PostMapping("add")
    public void addProduct(
            OrderEntry orderEntry,
            HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        ProductRepr productRepr = productService.findById(orderEntry.getId()).orElseThrow(NotFoundException::new);
        cartService.add(productRepr, orderEntry.getQty());
        response.sendRedirect(request.getHeader("referer"));
    }

    @GetMapping("remove")
    public void removeProduct(
            @RequestParam("productId") Long productId, @RequestParam("qty") Integer qty,
            HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        ProductRepr productRepr = productService.findById(productId).orElseThrow(NotFoundException::new);
        cartService.remove(productRepr, qty);
        response.sendRedirect(request.getHeader("referer"));
    }

}
