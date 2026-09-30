package com.webshop.dao;

import com.webshop.model.Order;
import com.webshop.model.OrderItem;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDB extends Order {
    private OrderDB(int id, int userId, LocalDateTime orderDate,
                    double totalPrice, String status, List<OrderItem> items) {
        super(id, userId, orderDate, totalPrice, status, items);
    }

    public static boolean placeOrder(Order order) {
        Connection conn = DBManager.getConnection();
        String sqlOrder = "INSERT INTO `Order` (userId, totalPrice) VALUES (?,?)";
        String sqlOrderItem = "INSERT INTO OrderItem (orderId, productId, quantity, unitPrice) VALUES (?,?,?,?)";
        String sqlUpdateStock = "UPDATE Product SET stock = stock - ? WHERE id = ?";
        int orderId;
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(
                        sqlOrder,
                        Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, order.getUserId());
                ps.setDouble(2, order.getTotalPrice());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (!rs.next()) {
                        throw new SQLException("Could not get generated order ID");
                    }
                    orderId = rs.getInt(1);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlOrderItem)) {
                for (OrderItem item : order.getItems()) {
                    ps.setInt(1, orderId);
                    ps.setInt(2, item.getProductId());
                    ps.setInt(3, item.getQuantity());
                    ps.setDouble(4, item.getUnitPrice());
                    ps.executeUpdate();
                }
            }
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdateStock)) {
                for (OrderItem item : order.getItems()) {
                    ps.setInt(1, item.getQuantity());
                    ps.setInt(2, item.getProductId());
                    ps.executeUpdate();
                }
            }
            conn.commit();
            return true;
        } catch(Exception e) {
            try {
                conn.rollback();
            } catch(SQLException sqlE) {sqlE.printStackTrace();}
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit((true));
            } catch (Exception e) {e.printStackTrace();}
        }

    }
}
