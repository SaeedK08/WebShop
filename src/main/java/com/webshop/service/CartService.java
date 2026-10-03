package com.webshop.service;

import com.webshop.dto.CartInfo;
import com.webshop.dto.CartItemInfo;
import com.webshop.model.Cart;
import com.webshop.model.CartItem;
import com.webshop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartService {

    private static Cart toModel(CartInfo cartInfo) {
        Cart cart = new Cart();
        if (cartInfo != null && cartInfo.getItems() != null) {
            for (CartItemInfo cinfo : cartInfo.getItems()) {
                cart.addItem(cinfo.getProductId(), cinfo.getProductName(), cinfo.getPrice(), cinfo.getQuantity());
            }
        }
        return cart;
    }

    private static CartInfo toDto(Cart cart) {
        List<CartItemInfo> updated = new ArrayList<>();
        double totalPrice = 0.0;
        for (CartItem ci : cart.getItems()) {
            updated.add(new CartItemInfo(ci.getId(), ci.getName(), ci.getPrice(), ci.getQuantity()));
            totalPrice += ci.getTotalPrice();
        }
        return new CartInfo(updated, totalPrice);
    }

    public static CartInfo addProductToCart(CartInfo cartInfo, int productId, int quantity) {
        if (quantity <= 0) return null;

        Product productToAdd = Product.getProductById(productId);
        if (productToAdd == null) return null;

        Cart cart = toModel(cartInfo);
        int currentQuantityInCart = cart.getQuantityForProduct(productToAdd.getId());

        if (currentQuantityInCart + quantity > productToAdd.getStock()) return null;

        cart.addItem(productToAdd.getId(), productToAdd.getName(), productToAdd.getPrice(), quantity);

        return toDto(cart);
    }

    public static CartInfo updateQuantity(CartInfo cartInfo, int productId, int quantity) {
        Product productToUpdate = Product.getProductById(productId);
        if (productToUpdate == null || quantity > productToUpdate.getStock()) {
            return null;
        }

        Cart cart = new Cart();
        for (CartItemInfo cinfo : cartInfo.getItems()) {
            int qtyToSet = (cinfo.getProductId() == productId) ? quantity : cinfo.getQuantity();
            cart.addItem(cinfo.getProductId(), cinfo.getProductName(), cinfo.getPrice(), qtyToSet);
        }

        return toDto(cart);
    }

    public static CartInfo removeCartItem(CartInfo cartInfo, int productId) {
        Cart cart = toModel(cartInfo);
        cart.removeItem(productId);
        return toDto(cart);
    }
}