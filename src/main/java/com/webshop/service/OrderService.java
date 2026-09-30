package com.webshop.service;

import com.webshop.dto.CartInfo;
import com.webshop.dto.CartItemInfo;
import com.webshop.model.Cart;
import com.webshop.model.Order;

import java.util.ArrayList;

public class OrderService {

    public static boolean placeOrder(int userId, CartInfo cartInfo) {
        if (cartInfo == null || cartInfo.getItems().isEmpty()) {
            return false;
        }
        Cart cart = new Cart();
        for (CartItemInfo cinfo : cartInfo.getItems()) {
            cart.addItem(cinfo.getProductId(), cinfo.getProductName(), cinfo.getPrice(), cinfo.getQuantity());
        }
        return Order.placeOrder(userId, cart);
    }
}
