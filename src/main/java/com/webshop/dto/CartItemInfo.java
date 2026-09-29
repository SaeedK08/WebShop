package com.webshop.dto;

public record CartItemInfo(int productId, String productName, int quantity, double price, double totalPrice) {
}