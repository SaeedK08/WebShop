package com.webshop.dto;

public record OrderItemInfo (
    String productName,
    int quantity,
    double unitPrice
){
    public String getProductName() {
        return productName();
    }
    public int getQuantity() {
        return quantity();
    }
    public double getUnitPrice() {
        return unitPrice();
    }
}
