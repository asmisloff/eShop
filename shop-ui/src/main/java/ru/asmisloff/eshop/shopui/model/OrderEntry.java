package ru.asmisloff.eshop.shopui.model;

import ru.asmisloff.eshop.common.repr.ProductRepr;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderEntry {

    private ProductRepr product;
    private int qty;
    private BigDecimal subTotal;

    public void update() {
        subTotal = product.getPrice().multiply(BigDecimal.valueOf(qty));
    }

    public ProductRepr getProduct() {
        return product;
    }

    public void setProduct(ProductRepr product) {
        this.product = product;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntry that = (OrderEntry) o;
        return product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }
}
