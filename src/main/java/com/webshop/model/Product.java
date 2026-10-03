package com.webshop.model;

import com.webshop.dao.ProductDB;

import java.util.List;

public class Product {
    private final String name;
    private final String description;
    private int id;
    private final double price;
    private final int stock;
    private final Integer categoryId;
    private String categoryName;

    protected Product(String name, String description, double price, int stock, Integer categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
    }
    protected Product(int id, String name, String description, double price, int stock, Integer categoryId, String categoryName) {
        this(name, description, price, stock, categoryId);
        this.categoryName = categoryName;
        this.id = id;
    }


    public static List<Product> getAllProducts() {
        return ProductDB.getAllProducts();
    }
    public static Product getProductById(int id) { return ProductDB.getProductById(id); }

    public static boolean create(String name, String description, double price, int stock, Integer categoryId) {
        return ProductDB.createProduct(new Product(0,name, description, price, stock, categoryId, null));
    }
    public static boolean update(int id, String name, String description, double price, int stock, Integer categoryId) {
        return ProductDB.updateProduct(new Product(id, name, description, price, stock, categoryId, null));
    }
    public static boolean updateStock(int productId, int stock) {return ProductDB.updateProductStock(productId, stock);}
    public static boolean delete(int productId) {
        return ProductDB.deleteProduct(productId);
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

    public Integer getCategoryId() {return categoryId;}

    public String getCategoryName() {return categoryName;}

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
