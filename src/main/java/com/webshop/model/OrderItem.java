package com.webshop.model;

public class OrderItem {
    private int id;
    private int orderId;
    private final int productId;
    private String productName;
    private final int quantity;
    private final double unitPrice;

    protected OrderItem(int id, int orderId, int productId, String productName, int quantity, double unitPrice) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    protected OrderItem(int productId, int quantity, double unitPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }
}
