package com.webshop.controller;

import com.webshop.dto.CartInfo;
import com.webshop.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.AreaAveragingScaleFilter;
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
        String action = request.getParameter("action");
        CartInfo cartInfo = (CartInfo) session.getAttribute("cartInfo");
        if (cartInfo == null) {
            cartInfo = new CartInfo(new ArrayList<>(), 0.0);
        }

        try {
            int productId = Integer.parseInt(request.getParameter("productId"));

            if ("add".equals(action)) {
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                CartInfo updated = CartService.addProductToCart(cartInfo, productId, quantity);

                if (updated == null) {
                    session.setAttribute("cartError", "Could not add item. Check available stock.");
                } else {
                    session.setAttribute("cartInfo", updated);
                }
            } else if ("remove".equals(action)) {
                CartInfo updated = CartService.removeCartItem(cartInfo, productId);
                session.setAttribute("cartInfo", updated);
            }
        } catch (NumberFormatException e) {
            session.setAttribute("cartError", "Invalid quantity or product identifier.");
        }

        response.sendRedirect(request.getContextPath() + "/cart");
    }
}