package com.webshop.controller;

import com.webshop.dto.LoginDTO;
import com.webshop.dto.UserInfo;
import com.webshop.service.LoginService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        LoginDTO loginData = new LoginDTO(username, password);
        UserInfo user = LoginService.validateUser(loginData);
        if (user == null) {
            req.setAttribute("errorMessage", "Wrong username or password");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
        req.getSession().setAttribute("currentUser", user);
        resp.sendRedirect(req.getContextPath() + "/products");
    }
}
