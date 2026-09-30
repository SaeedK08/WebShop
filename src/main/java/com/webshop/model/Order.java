package com.webshop.model;

import com.webshop.dao.OrderDB;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Order {
    private int id;
    private final int userId;
    private LocalDateTime orderDate;
    private final double totalPrice;
    private String status;
    private List<OrderItem> items;

    protected Order(int id, int userId, LocalDateTime orderDate, double totalPrice, String status, List<OrderItem> items) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.items = items;
    }

    protected Order(int userId, double totalPrice, List<OrderItem> items) {
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public static boolean placeOrder(int userId, Cart cart) {
        Order order = createOrder(userId, cart);
        return OrderDB.placeOrder(order);
    }

    private static Order createOrder(int userId, Cart cart) {
        if (cart == null || cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cannot create an order from an empty cart.");
        }
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem ci : cart.getItems()) {
            orderItems.add(new OrderItem(ci.getId(), ci.getQuantity(), ci.getPrice()));
        }
        return new Order(userId, cart.getTotalCartPrice(), orderItems);
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }
}
