package com.webshop.controller;

import com.webshop.dto.UserInfo;
import com.webshop.service.AdminUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminController extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        HttpSession session = req.getSession();
        UserInfo currentUser = (UserInfo) session.getAttribute("currentUser");
        if (currentUser == null || !currentUser.isAdmin()) {
            session.setAttribute("authError", "Admin panel requires admin privileges");
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }
        List<UserInfo> usersInfo = AdminUserService.getAllUsers();
        req.setAttribute("usersInfo", usersInfo);
        req.getRequestDispatcher("/WEB-INF/views/admin.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        UserInfo currentUser = (UserInfo) session.getAttribute("currentUser");
        if (currentUser == null || !currentUser.isAdmin()) {
            session.setAttribute("authError", "Admin panel requires admin privileges");
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) {
            resp.sendRedirect(req.getContextPath() + "/admin");
            return;
        }

        try {
            int targetUserId = Integer.parseInt(req.getParameter("userId"));
            boolean success = false;
            switch (action) {
                case "changePassword":
                    String newPassword = req.getParameter("password");
                    success = AdminUserService.changeUserPassword(targetUserId, newPassword);
                    break;
                case "changeUsername":
                    String newUsername = req.getParameter("username");
                    success = AdminUserService.changeUsername(targetUserId, newUsername);
                    break;
                case "changeRole":
                    String newRole = req.getParameter("role");
                    success = AdminUserService.changeUserRole(targetUserId, newRole);
                    break;
                case "delete":
                    success = AdminUserService.deleteUser(targetUserId, currentUser.getId());
                    break;
            }
            if (!success) {
                session.setAttribute("adminError", "Operation failed. Check input values or self-deletion restrictions.");
            } else {
                session.setAttribute("adminSuccess", "Action completed successfully.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("adminError", "Invalid user ID provided.");
        }
        resp.sendRedirect(req.getContextPath() + "/admin");
    }
}
