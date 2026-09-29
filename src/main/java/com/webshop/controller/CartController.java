package com.webshop.controller;

import com.webshop.dto.CartInfo;
import com.webshop.model.Cart;
import com.webshop.service.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/cart")
public class CartController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Hämta sessionen, skapa en kundvagn om den inte finns
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // Hämta DTO och skicka till vyn
        CartInfo cartInfo = CartService.getCartInfo(cart);
        request.setAttribute("cartInfo", cartInfo);
        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        String action = request.getParameter("action");
        if ("add".equals(action)) {
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            CartService.addToCart(cart, productId, quantity);
        }

        // Redirecta tillbaka till kundvagnen (förhindrar dubbla inlägg om användaren uppdaterar sidan)
        response.sendRedirect(request.getContextPath() + "/cart");
    }
}