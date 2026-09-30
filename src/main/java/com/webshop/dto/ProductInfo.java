package com.webshop.dto;

public record ProductInfo(int id, String name, String description, double price, int stock) {
    // private Category category;

    public int getId() { return id(); }
    public String getName() { return name(); }
    public String getDescription() { return description(); }
    public double getPrice() { return price(); }
    public int getStock() { return stock(); }
    public boolean isInStock() {
        return stock() > 0;
    }
}