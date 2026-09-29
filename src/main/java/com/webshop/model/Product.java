package com.webshop.model;

import com.webshop.dao.DBProduct;

import java.util.List;

public class Product {
    private final String name;
    private final String description;
    private int id;
    private final double price;
    private int stock;
    // private Category category;

    protected Product(String name, String description, double price, int stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }
    protected Product(int id, String name, String description, double price, int stock) {
        this(name, description, price, stock);
        this.id = id;
    }

    public static List<Product> getAllProducts() {
        return DBProduct.getAllProducts();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
