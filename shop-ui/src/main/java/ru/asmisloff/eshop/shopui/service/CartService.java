package ru.asmisloff.eshop.shopui.service;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import ru.asmisloff.eshop.common.repr.ProductRepr;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CartService {

    private Map<ProductRepr, Integer> orders = new HashMap<>();

    public void add(ProductRepr product, int qty) {
        if (!orders.containsKey(product)) {
            orders.put(product, qty);
        } else {
            orders.put(product, orders.get(product) + qty);
        }

    }

    public void remove(ProductRepr product, int qty) {
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

    public BigDecimal subTotal(ProductRepr productRepr) {
        if (!orders.containsKey(productRepr)) {
            throw new NoSuchElementException("No such key in orders: " + productRepr);
        }
        int qty = orders.get(productRepr);
        return productRepr.getPrice().multiply(BigDecimal.valueOf(qty));
    }

    public BigDecimal total() {
        return orders.keySet().stream()
                .map(key -> subTotal(key))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private int totalQty() {
        return orders.values().stream()
                .reduce((a, v) -> a + v)
                .orElse(0);
    }

    @Override
    public String toString() {
        return "CartService{" +
                "orders=" + orders +
                ", total=" + total() +
                ", totalQty=" + totalQty() +
                '}';
    }
}
