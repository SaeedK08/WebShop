package com.webshop.controller;

import com.webshop.dto.CartInfo;
import com.webshop.dto.ProductInfo;
import com.webshop.service.CartService;
import com.webshop.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/cart")
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        CartInfo cartInfo = (CartInfo) session.getAttribute("cartInfo");
        if (cartInfo == null) {
            session.setAttribute("cartInfo", new CartInfo(new ArrayList<>(), 0));
        }
        request.setAttribute("cartInfo", session.getAttribute("cartInfo"));
        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        CartInfo cartInfo = (CartInfo) session.getAttribute("cartInfo");
        if (cartInfo == null) {
            cartInfo = new CartInfo(new ArrayList<>(), 0.0);
        }

        try {
            CartInfo updated = null;
            int productId = Integer.parseInt(request.getParameter("productId"));
            String action = request.getParameter("action");
            String quantityParam = request.getParameter("quantity");
            int quantity=0;
            String redirectUrl = "/cart";

            if (quantityParam != null) {
                quantity = Integer.parseInt(quantityParam);
                if (quantity <= 0) action = "remove";
            }

            switch (action) {
                case "add" -> {
                    updated = CartService.addProductToCart(cartInfo, productId, quantity);
                    if (updated == null) {
                        session.setAttribute("cartError", "Could not add item. Check available stock.");
                    } else {
                        redirectUrl = "/products";
                    }
                }
                case "update" -> {
                    updated = CartService.updateQuantity(cartInfo, productId, quantity);
                    if (updated == null) {
                        ProductInfo currentProduct = ProductService.getProductById(productId);
                        session.setAttribute("cartError", "Only " + currentProduct.getStock() +
                                " items of " + currentProduct.getName() + " are available in stock.");
                    }
                }
                case "remove" -> {
                    updated = CartService.removeCartItem(cartInfo, productId);
                }
            }

            if (updated != null) {
                session.setAttribute("cartInfo", updated);
            }
            response.sendRedirect(request.getContextPath() + redirectUrl);

        } catch (NumberFormatException e) {
            session.setAttribute("cartError", "Invalid quantity or product identifier.");
            response.sendRedirect(request.getContextPath() + "/cart");
        }

    }
}