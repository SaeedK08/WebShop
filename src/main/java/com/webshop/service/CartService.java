package com.webshop.service;

import com.webshop.dto.CartInfo;
import com.webshop.dto.CartItemInfo;
import com.webshop.model.Cart;
import com.webshop.model.CartItem;
import com.webshop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartService {

    public static CartInfo addProductToCart(CartInfo cartInfo, int productId, int quantity) {
        if (quantity <= 0) return null;

        Product productToAdd = Product.getProductById(productId);
        if (productToAdd == null) return null;
        Cart cart = new Cart();
        for (CartItemInfo cinfo : cartInfo.getItems()) {
            cart.addItem(cinfo.getProductId(), cinfo.getProductName(), cinfo.getPrice(), cinfo.getQuantity());
        }
        int currentQuantityInCart = cart.getQuantityForProduct(productToAdd.getId());
        if (currentQuantityInCart + quantity > productToAdd.getStock())  return null;

        cart.addItem(productToAdd.getId(), productToAdd.getName(), productToAdd.getPrice(), quantity);

        List<CartItemInfo> updated = new ArrayList<>();
        double totalPrice = 0.0;
        for (CartItem ci : cart.getItems()) {
            updated.add(new CartItemInfo(ci.getId(), ci.getName(), ci.getPrice(), ci.getQuantity()));
            totalPrice += ci.getTotalPrice();
        }
        return new CartInfo(updated, totalPrice);
    }

    // Remove the whole item, does not respect quantity
    public static CartInfo removeCartItem (CartInfo cartInfo, int productId) {
        Cart cart = new Cart();
        for (CartItemInfo cinfo : cartInfo.getItems()) {
            cart.addItem(cinfo.getProductId(), cinfo.getProductName(), cinfo.getPrice(), cinfo.getQuantity()); //[cite: 13]
        }

        cart.removeItem(productId);

        List<CartItemInfo> updated = new ArrayList<>();
        double totalPrice = 0.0;
        for (CartItem ci : cart.getItems()) {
            updated.add(new CartItemInfo(ci.getId(), ci.getName(), ci.getPrice(), ci.getQuantity())); //[cite: 13]
            totalPrice += ci.getTotalPrice();
        }
        return new CartInfo(updated, totalPrice);
    }
}