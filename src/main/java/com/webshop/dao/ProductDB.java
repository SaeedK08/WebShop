package com.webshop.dao;

import com.webshop.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class ProductDB extends Product {
    private ProductDB(int id, String name, String description, double price,
                      int stock, Integer categoryId, String categoryName) {
        super(id, name, description, price, stock, categoryId, categoryName);
    }

    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String selectQuery = """
                SELECT p.id, p.name, p.description, p.price, p.stock, p.categoryId, c.name AS categoryName
                FROM Product p
                LEFT JOIN Category c ON p.categoryId = c.id
                """;
        Connection conn = DBManager.getConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int stock = resultSet.getInt("stock");
                Integer categoryId = resultSet.getObject("categoryId", Integer.class);
                String categoryName = resultSet.getString("categoryName");
                products.add(new ProductDB(id, name, description, price, stock, categoryId, categoryName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }
    public static Product getProductById(int id) {
        String selectQuery = """
                SELECT p.id, p.name, p.description, p.price, p.stock, p.categoryId, c.name AS categoryName
                FROM Product p
                LEFT JOIN Category c ON p.categoryId = c.id
                WHERE p.id = ?
                """;
        Connection conn = DBManager.getConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    return new ProductDB(resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getString("description"),
                                resultSet.getDouble("price"),
                                resultSet.getInt("stock"),
                                resultSet.getObject("categoryId", Integer.class),
                                resultSet.getString("categoryName"));
                }
            }

        } catch (Exception e) {e.printStackTrace();}
        return null;
    }

    public static boolean createProduct(Product product) {
        String sql = """
                INSERT INTO Product (name, description, price, stock, categoryId)
                VALUES (?, ?, ?, ?, ?)
                """;
        Connection conn = DBManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStock());

            if (product.getCategoryId() != null) {
                ps.setInt(5, product.getCategoryId());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }

            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateProduct(Product product) {
        String sql = """
                UPDATE Product
                SET name = ?, description = ?, price = ?, stock = ?, categoryId = ?
                WHERE id = ?
                """;
        Connection conn = DBManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStock());

            if (product.getCategoryId() != null) {
                ps.setInt(5, product.getCategoryId());
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            ps.setInt(6, product.getId());

            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteProduct(int productId) {
        String sql = "DELETE FROM Product WHERE id = ?";
        Connection conn = DBManager.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}


