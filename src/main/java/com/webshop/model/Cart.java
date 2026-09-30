package com.webshop.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {
    private final List<CartItem> items = new ArrayList<>();

    public void addItem(int id, String name, double price, int quantity) {
        for (CartItem ci : items) {
            if (ci.getId() == id) {
                ci.setQuantity(ci.getQuantity()+quantity);
                return;
            }
        }
        items.add(new CartItem(id, name, price, quantity));
    }

    public void removeItem(int productId) {
        items.removeIf(item -> item.getId() == productId);
    }

    public int getQuantityForProduct(int productId) {
        for (CartItem item : items) {
            if (item.getId() == productId) {
                return item.getQuantity();
            }
        }
        return 0;
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public double getTotalCartPrice() {
        double total = 0.0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }

    public void clear() {
        items.clear();
    }
}