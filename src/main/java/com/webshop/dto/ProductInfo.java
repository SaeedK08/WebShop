package com.webshop.dto;

public class ProductInfo {
    private final int id;
    private final String name;
    private final String description;
    private final double price;
    private final int stock;
    // private Category category;

    public ProductInfo(int id, String name, String description, double price, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {return name;}
    public String getDescription() {return description;}
    public double getPrice() {return this.price;}
    public int getStock() {return this.stock;}
    public boolean isInStock() {return this.stock>0;}

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
