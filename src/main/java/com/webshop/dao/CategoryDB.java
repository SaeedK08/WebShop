package com.webshop.dao;

import com.webshop.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDB extends Category {
    private CategoryDB(int id, String name, String description) {
        super(id, name, description);
    }

    public static List<Category> getAllCategories() {
        Connection conn = DBManager.getConnection();
        String sql = "SELECT * FROM Category";
        List<Category> categories = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                categories.add(new CategoryDB(rs.getInt("id"),
                                              rs.getString("name"),
                                              rs.getString("description")));
            }
            return categories;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static boolean createCategory(String name, String description) {
        Connection conn = DBManager.getConnection();
        String sql = "INSERT INTO Category (name, description) VALUES (?,?)";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, description);
            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new SQLException("Failed to add a new category");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateCategory(Category category) {
        Connection conn = DBManager.getConnection();
        String sql = "UPDATE Category SET name = ?, description = ? WHERE id=?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setInt(3, category.getId());
            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new SQLException("Failed to edit category name");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteCategory(int categoryId) {
        Connection conn = DBManager.getConnection();
        String sql = "DELETE FROM Category WHERE id = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            int rows = ps.executeUpdate();
            if (rows != 1) {
                throw new SQLException("Failed to delete category");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
