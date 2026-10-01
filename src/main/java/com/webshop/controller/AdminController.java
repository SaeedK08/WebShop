package com.webshop.controller;

import com.webshop.dto.CategoryInfo;
import com.webshop.dto.ProductInfo;
import com.webshop.dto.UserInfo;
import com.webshop.service.AdminUserService;
import com.webshop.service.CategoryService;
import com.webshop.service.ProductService;

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
        List<ProductInfo> products = ProductService.getAllProducts();
        req.setAttribute("products", products);
        List<CategoryInfo> categories = CategoryService.getAllCategories();
        req.setAttribute("categories", categories);
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
            boolean success = false;
            switch (action) {
                case "changePassword":
                    String newPassword = req.getParameter("password");
                    success = AdminUserService.changeUserPassword(
                            Integer.parseInt(req.getParameter("userId")), newPassword);
                    break;
                case "changeUsername":
                    String newUsername = req.getParameter("username");
                    success = AdminUserService.changeUsername(
                            Integer.parseInt(req.getParameter("userId")), newUsername);
                    break;
                case "changeRole":
                    String newRole = req.getParameter("role");
                    success = AdminUserService.changeUserRole(
                            Integer.parseInt(req.getParameter("userId")), newRole);
                    break;
                case "deleteUser":
                    success = AdminUserService.deleteUser(
                            Integer.parseInt(req.getParameter("userId")), currentUser.getId());
                    break;
                case "createCategory":
                    String catName = req.getParameter("name");
                    String catDescription = req.getParameter("description");
                    success = CategoryService.createCategory(catName, catDescription);
                    break;
                case "updateCategory":
                    int id = Integer.parseInt(req.getParameter("categoryId"));
                    String editName = req.getParameter("name");
                    String editDescription = req.getParameter("description");
                    success = CategoryService.editCategory(new CategoryInfo(id, editName, editDescription));
                    break;
                case "deleteCategory":
                    success = CategoryService.deleteCategory(Integer.parseInt(req.getParameter("categoryId")));
                    break;
                case "createProduct":
                    String name = req.getParameter("name");
                    String description = req.getParameter("description");
                    double price = Double.parseDouble(req.getParameter("price"));
                    int stock = Integer.parseInt(req.getParameter("stock"));
                    String catIdParam = req.getParameter("categoryId");
                    Integer catId = (catIdParam != null && !catIdParam.isBlank()) ?
                            Integer.parseInt(catIdParam) : null;
                    ProductInfo product = new ProductInfo(0, name, description, price, stock, catId, null);
                    success = ProductService.createProduct(product);
                    break;
                case "updateProduct":
                    int productId = Integer.parseInt(req.getParameter("productId"));
                    String editPName = req.getParameter("name");
                    String editPDescription = req.getParameter("description");
                    double editPrice = Double.parseDouble(req.getParameter("price"));
                    int editStock = Integer.parseInt(req.getParameter("stock"));
                    String editCatIdParam = req.getParameter("categoryId");
                    Integer editCatId = (editCatIdParam != null && !editCatIdParam.isBlank()) ?
                            Integer.parseInt(editCatIdParam) : null;
                    ProductInfo editProduct = new ProductInfo(productId, editPName, editPDescription, editPrice,
                            editStock, editCatId, null);
                    success = ProductService.updateProduct(editProduct);
                    break;
                case "deleteProduct":
                    success = ProductService.deleteProduct(Integer.parseInt(req.getParameter("productId")));
                    break;
                default:
                    break;
            }
            if (!success) {
                session.setAttribute("adminError", "Operation failed. Check inputs or database constraints.");
            } else {
                session.setAttribute("adminSuccess", "Action completed successfully.");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("adminError", "Invalid data format submitted.");
        }
        resp.sendRedirect(req.getContextPath() + "/admin");
    }
}
