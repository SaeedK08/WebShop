package com.webshop.dto;

public record ProductInfo(int id, String name, String description, double price,
                          int stock, Integer categoryId, String categoryName) {

    public int getId() { return id(); }
    public String getName() { return name(); }
    public String getDescription() { return description(); }
    public double getPrice() { return price(); }
    public int getStock() { return stock(); }
    public Integer getCategoryId() {return categoryId();}
    public String getCategoryName() {return categoryName();}
    public boolean isInStock() {
        return stock() > 0;
    }
}