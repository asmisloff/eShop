package ru.asmisloff.eshop.shopui.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.asmisloff.eshop.common.repr.ProductRepr;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartService {

    private Map<ProductRepr, Integer> orders = new HashMap<>();
    private BigDecimal total;
    private int totalQty;

    public void addProduct(ProductRepr product, int qty) {
        if (!orders.containsKey(product)) {
            orders.put(product, qty);
        } else {
            orders.put(product, orders.get(product) + qty);
        }

    }

    public void removeProduct(ProductRepr product, int qty) {
        if (!orders.containsKey(product)) {
            return;
        }

        if (orders.get(product) <= qty) {
            orders.remove(product);
        } else {
            orders.put(product, orders.get(product) - qty);
        }
    }

    public Map<ProductRepr, Integer> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return "CartService{" +
                "orders=" + orders +
                ", total=" + total +
                ", totalQty=" + totalQty +
                '}';
    }
}
