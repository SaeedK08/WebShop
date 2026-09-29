package com.webshop.dto;

public record ProductInfo(int id, String name, String description, double price, int stock) {
    // private Category category;

    public boolean isInStock() {
        return this.stock > 0;
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
