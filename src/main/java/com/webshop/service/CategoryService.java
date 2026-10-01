package com.webshop.service;

import com.webshop.dto.CategoryInfo;
import com.webshop.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryService {

    public static List<CategoryInfo> getAllCategories() {
        List<Category> categories = Category.getAllCategories();
        if (categories == null || categories.isEmpty()) {
            return new ArrayList<>();
        }
        List<CategoryInfo> categoryInfos = new ArrayList<>();
        for (Category c : categories) {
            categoryInfos.add(new CategoryInfo(c.getId(), c.getName(), c.getDescription()));
        }
        return categoryInfos;
    }

    public static boolean createCategory(String name, String description) {
        return Category.create(name, description);
    }

    public static boolean editCategory(CategoryInfo categoryInfo) {
        return Category.update(categoryInfo.getId(), categoryInfo.getName(), categoryInfo.getDescription());
    }

    public static boolean deleteCategory(int categoryId) {
        return Category.delete(categoryId);
    }
}
