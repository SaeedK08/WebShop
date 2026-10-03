<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Staff Dashboard - WebShop</title>
    <style>
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f6f9; margin: 0; padding: 20px; color: #333; }
        .header { display: flex; justify-content: space-between; align-items: center; background-color: #343a40; color: white; padding: 15px 20px; border-radius: 8px; margin-bottom: 20px; }
        .header a { color: #17a2b8; text-decoration: none; font-weight: bold; margin-left: 15px; }
        .header a:hover { text-decoration: underline; }
        .card { background: white; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); padding: 20px; margin-bottom: 30px; }
        .card h2 { margin-top: 0; border-bottom: 2px solid #eee; padding-bottom: 10px; color: #495057; }
        .msg-success { background-color: #d4edda; color: #155724; padding: 10px; border-radius: 4px; border: 1px solid #c3e6cb; margin-bottom: 20px; font-weight: bold; }
        .msg-error { background-color: #f8d7da; color: #721c24; padding: 10px; border-radius: 4px; border: 1px solid #f5c6cb; margin-bottom: 20px; font-weight: bold; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #dee2e6; }
        th { background-color: #f8f9fa; font-weight: bold; }
        .btn { padding: 6px 12px; border: none; border-radius: 4px; cursor: pointer; font-weight: bold; }
        .btn-success { background-color: #28a745; color: white; }
        .btn-primary { background-color: #007bff; color: white; }
        .badge { padding: 4px 8px; border-radius: 4px; font-size: 0.9em; font-weight: bold; }
        .badge-pending { background-color: #fff3cd; color: #856404; }
        .badge-packed { background-color: #d4edda; color: #155724; }
        .update-form { display: flex; gap: 10px; align-items: center; margin: 0; }
        .update-form input[type="number"] { width: 70px; padding: 5px; border: 1px solid #ccc; border-radius: 4px; }
    </style>
</head>
<body>

    <!-- Header Navigation -->
    <div class="header">
        <div>
            <h1 style="margin: 0; font-size: 1.5em;">Warehouse Dashboard</h1>
        </div>
        <div>
            <span>Logged in as: <strong><c:out value="${sessionScope.currentUser.username}" /></strong> (Staff)</span>
            <a href="${pageContext.request.contextPath}/products">← Storefront</a>
            <a href="${pageContext.request.contextPath}/logout" style="color: #ff6b6b;">Logout</a>
        </div>
    </div>

    <!-- Feedback Messages -->
    <c:if test="${not empty sessionScope.staffSuccess}">
        <div class="msg-success"><c:out value="${sessionScope.staffSuccess}" /></div>
        <c:remove var="staffSuccess" scope="session" />
    </c:if>
    <c:if test="${not empty sessionScope.staffError}">
        <div class="msg-error"><c:out value="${sessionScope.staffError}" /></div>
        <c:remove var="staffError" scope="session" />
    </c:if>

    <!-- SECTION 1: Order Fulfillment -->
    <div class="card">
        <h2>📦 Order Fulfillment</h2>
        <c:choose>
            <c:when test="${empty orders}">
                <p>No orders found.</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>Order #</th>
                            <th>Date</th>
                            <th>Customer</th>
                            <th>Items</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="o" items="${orders}">
                            <tr>
                                <td><strong>#<c:out value="${o.id}" /></strong></td>
                                <td><c:out value="${o.orderDate}" /></td>
                                <td><c:out value="${o.username}" /></td>
                                <td>
                                    <ul style="margin: 0; padding-left: 20px;">
                                        <c:forEach var="item" items="${o.items}">
                                            <li><c:out value="${item.quantity}" />x <c:out value="${item.productName}" /></li>
                                        </c:forEach>
                                    </ul>
                                </td>
                                <td>
                                    <span class="badge ${o.status == 'PENDING' ? 'badge-pending' : 'badge-packed'}">
                                        <c:out value="${o.status}" />
                                    </span>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${o.status == 'PENDING'}">
                                            <form action="${pageContext.request.contextPath}/staff" method="post" style="margin: 0;">
                                                <input type="hidden" name="action" value="packOrder" />
                                                <input type="hidden" name="orderId" value="${o.id}" />
                                                <button type="submit" class="btn btn-success">Pack Order</button>
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <span style="color: #6c757d; font-size: 0.9em;">Completed</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- SECTION 2: Inventory Management -->
    <div class="card">
        <h2>📊 Inventory Management</h2>
        <c:choose>
            <c:when test="${empty products}">
                <p>No products found in the catalog.</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Product Name</th>
                            <th>Price</th>
                            <th>Category</th>
                            <th>Current Stock</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${products}">
                            <tr>
                                <td><c:out value="${p.id}" /></td>
                                <td><strong><c:out value="${p.name}" /></strong></td>
                                <td><c:out value="${p.price}" /> SEK</td>
                                <td><c:out value="${empty p.categoryName ? 'Uncategorized' : p.categoryName}" /></td>
                                <td>
                                    <span style="color: ${p.stock <= 5 ? 'red' : 'green'}; font-weight: bold;">
                                        <c:out value="${p.stock}" />
                                    </span>
                                </td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/staff" method="post" class="update-form">
                                        <input type="hidden" name="action" value="updateStock" />
                                        <input type="hidden" name="productId" value="${p.id}" />
                                        <input type="number" name="stock" value="${p.stock}" min="0" required />
                                        <button type="submit" class="btn btn-primary">Update</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>

</body>
</html>