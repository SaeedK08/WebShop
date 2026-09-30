package com.webshop.dto;

public record CartItemInfo(int productId, String productName, double price, int quantity) {
    public int getProductId() {
        return productId();
    }

    public String getProductName() {
        return productName();
    }


    public int getQuantity() {
        return quantity();
    }

    public double getPrice() {
        return price();
    }

    public double getTotalPrice() {return price()*quantity();}

}