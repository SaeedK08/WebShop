package com.webshop.service;

import com.webshop.dto.CartInfo;
import com.webshop.dto.CartItemInfo;
import com.webshop.dto.OrderInfo;
import com.webshop.dto.OrderItemInfo;
import com.webshop.model.Cart;
import com.webshop.model.Order;
import com.webshop.model.OrderItem;

import java.util.ArrayList;
import java.util.List;


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

    public static List<OrderInfo> getAllOrders() {
        List<Order> orders = Order.getAllOrders();
        return fetchOrderInfos(orders);
    }
    public static List<OrderInfo> getOrdersByUserId(int userId) {
        List<Order> orders = Order.getOrdersByUserId(userId);
        return fetchOrderInfos(orders);
    }

    public static List<OrderInfo> fetchOrderInfos(List<Order> orders) {
        List<OrderInfo> orderInfos = new ArrayList<>();
        for (Order o : orders) {
            List<OrderItemInfo> orderItemInfos = new ArrayList<>();
            for (OrderItem oi : o.getItems()) {
                orderItemInfos.add(new OrderItemInfo(oi.getProductName(), oi.getQuantity(), oi.getUnitPrice()));
            }
            orderInfos.add(new OrderInfo(o.getId(), o.getUsername(), o.getOrderDate(),
                            o.getTotalPrice(), o.getStatus(), orderItemInfos));
        }
        return orderInfos;
    }

    public static boolean packOrder(int orderId) {
        return Order.packOrder(orderId);
    }
}
