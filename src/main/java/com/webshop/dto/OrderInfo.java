package com.webshop.dto;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record OrderInfo (
        int id,
        String username,
        LocalDateTime orderDate,
        double totalPrice,
        String status,
        List<OrderItemInfo> items
){
    public int getId() {
        return id();
    }

    public String getUsername() {
        return username();
    }

    public LocalDateTime getOrderDate() {
        return orderDate();
    }

    public double getTotalPrice() {
        return totalPrice();
    }

    public String getStatus() {
        return status();
    }

    public List<OrderItemInfo> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean isPending() {
        return "PENDING".equalsIgnoreCase(status);
    }
}
