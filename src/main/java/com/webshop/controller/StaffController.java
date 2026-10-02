package com.webshop.controller;

import com.webshop.dto.OrderInfo;
import com.webshop.dto.UserInfo;
import com.webshop.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/staff")
public class StaffController extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserInfo currentUser = (UserInfo) session.getAttribute("currentUser");
        if (currentUser == null || !currentUser.isStaff()) {
            session.setAttribute("authError", "Staff panel requires staff privileges");
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }
        req.setAttribute("orders", OrderService.getAllOrders());
        req.getRequestDispatcher("/WEB-INF/views/staff.jsp").forward(req, resp);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserInfo currentUser = (UserInfo) session.getAttribute("currentUser");
        if (currentUser == null || !currentUser.isStaff()) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        String action = req.getParameter("action");
        if ("packOrder".equals(action)) {
            boolean success = false;
            try {
                int orderId = Integer.parseInt(req.getParameter("orderId"));
                success = OrderService.packOrder(orderId);
                if (success) {
                    session.setAttribute("staffSuccess", "Order #" + orderId + " marked as PACKED.");
                } else {
                    session.setAttribute("staffError", "Failed to pack order #" + orderId + ".");
                }
            } catch (Exception e) {
                session.setAttribute("staffError", "Invalid order ID.");
            }
        }
        resp.sendRedirect(req.getContextPath() + "/staff");
    }


}
