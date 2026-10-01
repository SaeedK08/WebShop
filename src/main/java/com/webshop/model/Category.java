package com.webshop.model;

import com.webshop.dao.CategoryDB;

import java.util.List;

public class Category {
    private int id;
    private String name;
    private String description;

    protected Category(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    protected Category(String name, String description) {
        this(0, name, description);
    }

    public static List<Category> getAllCategories() {
        return CategoryDB.getAllCategories();
    }
    public static boolean create(String name, String description) {
        return CategoryDB.createCategory(name, description);
    }
    public static boolean update(int id, String name, String description) {
        return CategoryDB.updateCategory(new Category(id, name, description));
    }
    public static boolean delete(int id) {
        return CategoryDB.deleteCategory(id);
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public String getDescription() {return description;}


}
