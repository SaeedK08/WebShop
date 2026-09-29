package com.webshop.dao;

import com.webshop.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDB extends Product {
    private ProductDB(int id, String name, String description, double price, int stock) {
        super(id, name, description, price, stock);
    }

    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String selectQuery = "SELECT * FROM Product";
        Connection conn = DBManager.getConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery();) {
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                int stock = resultSet.getInt("stock");
                products.add(new ProductDB(id, name, description, price, stock));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }
    public static Product getProductById(int id) {
        String selectQuery = "SELECT * FROM Product WHERE id = ?";
        Connection conn = DBManager.getConnection();
        try (PreparedStatement preparedStatement = conn.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new ProductDB(resultSet.getInt("id"),
                                        resultSet.getString("name"),
                                        resultSet.getString("description"),
                                        resultSet.getDouble("price"),
                                        resultSet.getInt("stock"));

            }
        } catch (Exception e) {e.printStackTrace();}
        return null;
    }


//    public static void add (Product product) {
//
//    }
//    public static void updateProduct(int id) {}
//    public static void deleteProduct(int id) {}
//    public static boolean decreaseStock(int id) {return true;}
}


