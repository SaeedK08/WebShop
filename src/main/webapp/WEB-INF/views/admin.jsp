<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - WebShop</title>
</head>
<body style="font-family: sans-serif; padding: 20px;">

    <!-- Top Navigation -->
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px;">
        <a href="${pageContext.request.contextPath}/products"
           style="text-decoration: none; font-weight: bold; padding: 6px 12px; background-color: #eee; border: 1px solid #ccc; border-radius: 4px; color: #333;">
            ← Back to Products
        </a>
        <span>Signed in as Admin: <strong><c:out value="${sessionScope.currentUser.username}" /></strong></span>
    </div>

    <hr/>

    <!-- Alerts -->
    <c:if test="${not empty sessionScope.adminSuccess}">
        <p style="color: green; font-weight: bold;"><c:out value="${sessionScope.adminSuccess}" /></p>
        <c:remove var="adminSuccess" scope="session" />
    </c:if>

    <c:if test="${not empty sessionScope.adminError}">
        <p style="color: red; font-weight: bold;"><c:out value="${sessionScope.adminError}" /></p>
        <c:remove var="adminError" scope="session" />
    </c:if>

    <!-- ================= CATEGORIES SECTION ================= -->
    <h2>Category Management</h2>
    <table border="1" cellpadding="6" cellspacing="0" style="border-collapse: collapse; width: 100%; margin-bottom: 15px;">
        <tr style="background-color: #f2f2f2;">
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="c" items="${categories}">
            <tr>
                <form action="${pageContext.request.contextPath}/admin" method="post">
                    <input type="hidden" name="action" value="updateCategory" />
                    <input type="hidden" name="categoryId" value="${c.id}" />
                    <td><c:out value="${c.id}" /></td>
                    <td><input type="text" name="name" value="${c.name}" required /></td>
                    <td><input type="text" name="description" value="${c.description}" style="width: 250px;" /></td>
                    <td>
                        <button type="submit">Save</button>
                </form>
                        <form action="${pageContext.request.contextPath}/admin" method="post" style="display:inline;" onsubmit="return confirm('Delete category? Products in it will be uncategorized.');">
                            <input type="hidden" name="action" value="deleteCategory" />
                            <input type="hidden" name="categoryId" value="${c.id}" />
                            <button type="submit" style="color: red;">Delete</button>
                        </form>
                    </td>
            </tr>
        </c:forEach>
    </table>

    <details style="margin-bottom: 30px;">
        <summary style="font-weight: bold; cursor: pointer;">+ Add New Category</summary>
        <form action="${pageContext.request.contextPath}/admin" method="post" style="margin-top: 10px; padding: 10px; border: 1px solid #ccc; width: 350px;">
            <input type="hidden" name="action" value="createCategory" />
            <p>Name: <br/><input type="text" name="name" required style="width: 100%;" /></p>
            <p>Description: <br/><textarea name="description" style="width: 100%;"></textarea></p>
            <button type="submit">Create Category</button>
        </form>
    </details>

    <hr/>

    <!-- ================= PRODUCTS SECTION ================= -->
    <h2>Product Management</h2>
    <table border="1" cellpadding="6" cellspacing="0" style="border-collapse: collapse; width: 100%; margin-bottom: 15px;">
        <tr style="background-color: #f2f2f2;">
            <th>ID</th>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Stock</th>
            <th>Category</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="p" items="${products}">
            <tr>
                <form action="${pageContext.request.contextPath}/admin" method="post">
                    <input type="hidden" name="action" value="updateProduct" />
                    <input type="hidden" name="productId" value="${p.id}" />
                    <td><c:out value="${p.id}" /></td>
                    <td><input type="text" name="name" value="${p.name}" required /></td>
                    <td><input type="text" name="description" value="${p.description}" /></td>
                    <td><input type="number" step="0.01" name="price" value="${p.price}" style="width: 70px;" required /></td>
                    <td><input type="number" name="stock" value="${p.stock}" style="width: 50px;" required /></td>
                    <td>
                        <select name="categoryId">
                            <option value="">-- None --</option>
                            <c:forEach var="cat" items="${categories}">
                                <option value="${cat.id}" ${p.categoryId == cat.id ? 'selected' : ''}>
                                    <c:out value="${cat.name}" />
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <button type="submit">Save</button>
                </form>
                        <form action="${pageContext.request.contextPath}/admin" method="post" style="display:inline;" onsubmit="return confirm('Delete this product?');">
                            <input type="hidden" name="action" value="deleteProduct" />
                            <input type="hidden" name="productId" value="${p.id}" />
                            <button type="submit" style="color: red;">Delete</button>
                        </form>
                    </td>
            </tr>
        </c:forEach>
    </table>

    <details style="margin-bottom: 30px;">
        <summary style="font-weight: bold; cursor: pointer;">+ Add New Product</summary>
        <form action="${pageContext.request.contextPath}/admin" method="post" style="margin-top: 10px; padding: 10px; border: 1px solid #ccc; width: 400px;">
            <input type="hidden" name="action" value="createProduct" />
            <p>Name: <br/><input type="text" name="name" required style="width: 100%;" /></p>
            <p>Description: <br/><textarea name="description" style="width: 100%;"></textarea></p>
            <p>Price (SEK): <br/><input type="number" step="0.01" name="price" required style="width: 100%;" /></p>
            <p>Stock: <br/><input type="number" name="stock" value="10" required style="width: 100%;" /></p>
            <p>Category: <br/>
                <select name="categoryId" style="width: 100%;">
                    <option value="">-- None --</option>
                    <c:forEach var="cat" items="${categories}">
                        <option value="${cat.id}"><c:out value="${cat.name}" /></option>
                    </c:forEach>
                </select>
            </p>
            <button type="submit">Create Product</button>
        </form>
    </details>

    <hr/>

    <!-- ================= USERS SECTION ================= -->
    <h2>User Administration</h2>
    <table border="1" cellpadding="6" cellspacing="0" style="border-collapse: collapse; width: 100%;">
        <tr style="background-color: #f2f2f2;">
            <th>ID</th>
            <th>Username</th>
            <th>Role</th>
            <th>Change Role</th>
            <th>Change Password</th>
            <th>Delete</th>
        </tr>
        <c:forEach var="u" items="${usersInfo}">
            <tr>
                <td><c:out value="${u.id}" /></td>
                <td><c:out value="${u.username}" /></td>
                <td><strong><c:out value="${u.role}" /></strong></td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin" method="post" style="display:inline; margin: 0;">
                        <input type="hidden" name="action" value="changeRole" />
                        <input type="hidden" name="userId" value="${u.id}" />
                        <select name="role">
                            <option value="CUSTOMER" ${u.role == 'CUSTOMER' ? 'selected' : ''}>CUSTOMER</option>
                            <option value="STAFF" ${u.role == 'STAFF' ? 'selected' : ''}>STAFF</option>
                            <option value="ADMIN" ${u.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
                        </select>
                        <button type="submit">Update</button>
                    </form>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin" method="post" style="display:inline; margin: 0;">
                        <input type="hidden" name="action" value="changePassword" />
                        <input type="hidden" name="userId" value="${u.id}" />
                        <input type="password" name="password" placeholder="New password" required style="width: 110px;" />
                        <button type="submit">Set</button>
                    </form>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${u.id == sessionScope.currentUser.id}">
                            <em>(Current User)</em>
                        </c:when>
                        <c:otherwise>
                            <form action="${pageContext.request.contextPath}/admin" method="post" style="display:inline; margin: 0;"
                                  onsubmit="return confirm('Delete user ${u.username}?');">
                                <input type="hidden" name="action" value="deleteUser" />
                                <input type="hidden" name="userId" value="${u.id}" />
                                <button type="submit" style="color: red;">Delete</button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>