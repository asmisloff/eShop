package ru.asmisloff.eshop.shopui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.asmisloff.eshop.common.error.NotFoundException;
import ru.asmisloff.eshop.common.repr.ProductRepr;
import ru.asmisloff.eshop.common.service.ProductService;
import ru.asmisloff.eshop.shopui.service.CartService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping
    @ResponseBody
    public Map<ProductRepr, Integer> showCart() {
        return cartService.getOrders();
    }

    @RequestMapping("add")
    public void addProduct(
            @RequestParam("productId") Long productId, @RequestParam("qty") Integer qty,
            HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        ProductRepr productRepr = productService.findById(productId).orElseThrow(NotFoundException::new);
        cartService.addProduct(productRepr, qty);
        response.sendRedirect(request.getHeader("referer"));
    }

}
