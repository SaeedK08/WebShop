package com.webshop.dto;

public class CategoryInfo {
    private final int id;
    private final String name;
    private final String description;

    public CategoryInfo(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public CategoryInfo(String name, String description) {
        this(0, name, description);
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public String getDescription() {return description;}
}
