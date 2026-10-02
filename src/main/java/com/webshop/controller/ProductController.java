package com.webshop.controller;

import com.webshop.dto.OrderInfo;
import com.webshop.dto.ProductInfo;
import com.webshop.dto.UserInfo;
import com.webshop.service.OrderService;
import com.webshop.service.ProductService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.rmi.ServerException;
import java.util.List;


@WebServlet("/products")
public class ProductController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServerException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("currentUser") == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            UserInfo user = (UserInfo) session.getAttribute("currentUser");
            List<ProductInfo> products = ProductService.getAllProducts();
            request.setAttribute("products", products);
            request.setAttribute("orders", OrderService.getOrdersByUserId(user.getId()));
            request.getRequestDispatcher("/WEB-INF/views/product.jsp").forward(request, response);
        } catch(Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Can't fetch products");
        }
    }
}
