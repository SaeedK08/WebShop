package com.webshop.dao;

import com.webshop.model.Order;
import com.webshop.model.OrderItem;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrderDB extends Order {
    private static final String BASE_ORDER_QUERY = """
            SELECT
                o.id AS orderId,
                o.userId,
                u.username,
                o.orderDate,
                o.totalPrice,
                o.status,
                oi.id AS orderItemId,
                oi.productId,
                p.name AS productName,
                oi.quantity,
                oi.unitPrice
            FROM `Order` o
            JOIN User u ON o.userId = u.id
            JOIN OrderItem oi ON oi.orderId = o.id
            JOIN Product p ON oi.productId = p.id
            """;
    private static class OrderItemDB extends OrderItem {
        private OrderItemDB(int id, int orderId, int productId, String productName, int quantity, double unitPrice) {
            super(id, orderId, productId, productName, quantity, unitPrice);
        }
    }
    private OrderDB(int id, int userId, String username, LocalDateTime orderDate,
                    double totalPrice, String status, List<OrderItem> items) {
        super(id, userId, username, orderDate, totalPrice, status, items);
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

    public static boolean packOrder(int orderId) {
        Connection conn = DBManager.getConnection();
        String sql = "UPDATE `Order` SET status = 'PACKED' WHERE id = ? AND status = 'PENDING'";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            int rs = ps.executeUpdate();
            if (rs != 1) {
                throw new SQLException("Failed to pack order: order not found or not in PENDING state.");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Order> getAllOrders() {
        String sql = BASE_ORDER_QUERY + " ORDER BY o.orderDate DESC, o.id DESC";
        return fetchOrders(sql, null);
    }

    public static List<Order> getOrdersByUserId(int userId) {
        String sql = BASE_ORDER_QUERY + " WHERE o.userId = ? ORDER BY o.orderDate DESC, o.id DESC";
        return fetchOrders(sql, userId);
    }


    private static List<Order> fetchOrders(String sql, Integer userId) {
        Connection conn = DBManager.getConnection();
        Map<Integer, OrderDB> ordersMap = new LinkedHashMap<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (userId != null) {
                ps.setInt(1, userId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int orderId = rs.getInt("orderId");
                    OrderDB order = ordersMap.get(orderId);

                    if (order == null) {
                        order = new OrderDB(
                                orderId,
                                rs.getInt("userId"),
                                rs.getString("username"),
                                rs.getTimestamp("orderDate").toLocalDateTime(),
                                rs.getDouble("totalPrice"),
                                rs.getString("status"),
                                new ArrayList<>()
                        );
                        ordersMap.put(orderId, order);
                    }

                    order.addItem(new OrderItemDB(
                            rs.getInt("orderItemId"),
                            orderId,
                            rs.getInt("productId"),
                            rs.getString("productName"),
                            rs.getInt("quantity"),
                            rs.getDouble("unitPrice")
                    ));
                }
            }
            return new ArrayList<>(ordersMap.values());
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
