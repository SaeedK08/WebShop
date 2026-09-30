package com.webshop.dto;

import java.util.Collections;
import java.util.List;

public record CartInfo(List<CartItemInfo> items, double totalCartPrice) {
    public List<CartItemInfo> getItems() {
        return Collections.unmodifiableList(items);
    }

    public double getTotalCartPrice() {
        return totalCartPrice();
    }
}