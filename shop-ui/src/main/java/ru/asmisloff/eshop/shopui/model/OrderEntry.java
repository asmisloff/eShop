package ru.asmisloff.eshop.shopui.model;

public class OrderEntry {

    private Long id;
    private Integer qty;

    public OrderEntry(Long id, Integer qty) {
        this.id = id;
        this.qty = qty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
