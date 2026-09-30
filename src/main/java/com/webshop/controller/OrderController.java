package com.webshop.controller;

import com.webshop.dto.CartInfo;
import com.webshop.dto.UserInfo;
import com.webshop.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/checkout")
public class OrderController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserInfo user = (UserInfo) session.getAttribute("currentUser");
        if (user == null) {
            session.setAttribute("authError", "You must be signed in to checkout");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        CartInfo cartInfo = (CartInfo) session.getAttribute("cartInfo");
        if (cartInfo == null || cartInfo.getItems().isEmpty()) {
            session.setAttribute("cartError", "You cart is empty, fill it to place an order");
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }

        boolean success = OrderService.placeOrder(user.getId(), cartInfo);
        if (success) {
            session.removeAttribute("cartInfo");
            session.setAttribute("checkoutSuccess", "Thank you for your order! It is now being processed.");
            resp.sendRedirect(req.getContextPath() + "/products");
        }
        else {
            session.setAttribute("cartError", "Checkout failed. Please check stock availability or try again.");
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }
}

