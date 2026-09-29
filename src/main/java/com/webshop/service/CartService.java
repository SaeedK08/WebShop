package com.webshop.service;

import com.webshop.dao.DBProduct;
import com.webshop.dto.CartInfo;
import com.webshop.dto.CartItemInfo;
import com.webshop.model.Cart;
import com.webshop.model.CartItem;
import com.webshop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartService {

    // Lägger till en produkt i kundvagnen via databasen
    public static void addToCart(Cart cart, int productId, int quantity) {
        Product product = DBProduct.getProductById(productId);
        if (product != null) {
            cart.addItem(product, quantity);
        }
    }

    // Konverterar domänmodellen (Cart) till en DTO (CartInfo) för JSP-sidan
    public static CartInfo getCartInfo(Cart cart) {
        List<CartItemInfo> itemInfos = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            itemInfos.add(new CartItemInfo(
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getProduct().getPrice(),
                    item.getTotalPrice()
            ));
        }
        return new CartInfo(itemInfos, cart.getTotalCartPrice());
    }
}