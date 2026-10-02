<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Products - WebShop</title>
    <style>
        dialog::backdrop {
            background-color: rgba(0, 0, 0, 0.5);
        }
        dialog {
            border: 1px solid #ccc;
            border-radius: 8px;
            padding: 20px;
            width: 80%;
            max-width: 750px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.25);
        }
    </style>
</head>
<body>
    <!-- Användarmeny & Navigering -->
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px;">
        <div>
            <c:choose>
                <c:when test="${not empty sessionScope.currentUser}">
                    <span>Signed in as: <strong><c:out value="${sessionScope.currentUser.username}" /></strong> (<c:out value="${sessionScope.currentUser.role}" />)</span>

                    <!-- My Orders Popup Button -->
                    <button type="button" onclick="document.getElementById('ordersDialog').showModal()"
                            style="margin-left: 12px; padding: 4px 10px; cursor: pointer; border-radius: 3px; border: 1px solid #666; background-color: #f7f7f7;">
                        📦 My Orders
                    </button>

                    <c:if test="${sessionScope.currentUser.staff || sessionScope.currentUser.admin}">
                        <a href="${pageContext.request.contextPath}/staff"
                           style="margin-left: 12px; padding: 4px 8px; background-color: #007bff; color: white; text-decoration: none; border-radius: 3px; font-size: 0.9em;">
                            Staff Panel
                        </a>
                    </c:if>

                    <c:if test="${sessionScope.currentUser.admin}">
                        <a href="${pageContext.request.contextPath}/admin"
                           style="margin-left: 12px; padding: 4px 8px; background-color: #333; color: white; text-decoration: none; border-radius: 3px; font-size: 0.9em;">
                            Admin Panel
                        </a>
                    </c:if>

                    <span style="margin: 0 10px;">|</span>
                    <a href="${pageContext.request.contextPath}/logout">Sign Out</a>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login">Sign In</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <hr/>

    <!-- Header-rad med titel och kundvagn -->
    <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 15px; margin-bottom: 15px;">
        <h1 style="margin: 0;">Our Products</h1>
        <div>
            <a href="${pageContext.request.contextPath}/cart"
               style="font-weight: bold; text-decoration: none; padding: 6px 12px; border: 1px solid #333; border-radius: 4px; color: #333;">
                🛒 View Shopping Cart (<c:out value="${sessionScope.cartInfo != null ? sessionScope.cartInfo.items.size() : 0}" />)
            </a>
        </div>
    </div>

    <!-- Feedback & Felmeddelanden -->
    <c:if test="${not empty sessionScope.authError}">
        <p style="color: red; font-weight: bold;">
            <c:out value="${sessionScope.authError}" />
        </p>
        <c:remove var="authError" scope="session" />
    </c:if>

    <c:if test="${not empty sessionScope.checkoutSuccess}">
        <p style="color: green; font-weight: bold;">
            <c:out value="${sessionScope.checkoutSuccess}" />
        </p>
        <c:remove var="checkoutSuccess" scope="session" />
    </c:if>

    <c:if test="${not empty sessionScope.cartError}">
        <p style="color: red; font-weight: bold;">
            <c:out value="${sessionScope.cartError}" />
        </p>
        <c:remove var="cartError" scope="session" />
    </c:if>

    <!-- Produkttabell -->
    <table border="1" cellpadding="8" cellspacing="0" style="width: 100%; border-collapse: collapse; margin-top: 10px;">
        <thead>
            <tr style="background-color: #f2f2f2;">
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>In Stock</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="p" items="${products}">
                <tr>
                    <td><c:out value="${p.name}" /></td>
                    <td><c:out value="${p.description}" /></td>
                    <td><c:out value="${p.price}" /> SEK</td>
                    <td>
                        <c:choose>
                            <c:when test="${p.inStock}">
                                Yes
                            </c:when>
                            <c:otherwise>
                                No
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${p.inStock}">
                                <form action="${pageContext.request.contextPath}/cart" method="post" style="margin: 0;">
                                    <input type="hidden" name="productId" value="${p.id}">
                                    <input type="hidden" name="action" value="add">
                                    <input type="number" name="quantity" value="1" min="1" max="${p.stock}" style="width: 50px;">
                                    <button type="submit">Add to Cart</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <em>Unavailable</em>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- ================= MY ORDERS POPUP MODAL ================= -->
    <dialog id="ordersDialog">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px;">
            <h2 style="margin: 0;">My Orders</h2>
            <button type="button" onclick="document.getElementById('ordersDialog').close()" style="cursor: pointer; font-size: 16px;">✕</button>
        </div>
        <hr/>

        <div style="max-height: 400px; overflow-y: auto; margin-top: 15px;">
            <c:choose>
                <c:when test="${empty orders}">
                    <p>You have no registered orders.</p>
                </c:when>
                <c:otherwise>
                    <table border="1" cellpadding="6" cellspacing="0" style="border-collapse: collapse; width: 100%;">
                        <tr style="background-color: #f9f9f9;">
                            <th>Order #</th>
                            <th>Date</th>
                            <th>Total</th>
                            <th>Status</th>
                            <th>Details</th>
                        </tr>
                        <c:forEach var="o" items="${orders}">
                            <tr>
                                <td align="center">#<c:out value="${o.id}" /></td>
                                <td><c:out value="${o.orderDate}" /></td>
                                <td><c:out value="${o.totalPrice}" /> SEK</td>
                                <td align="center">
                                    <span style="font-weight: bold; color: ${o.status == 'PENDING' ? '#d39e00' : '#28a745'};">
                                        <c:out value="${o.status}" />
                                    </span>
                                </td>
                                <td>
                                    <ul style="margin: 0; padding-left: 18px;">
                                        <c:forEach var="item" items="${o.items}">
                                            <li>
                                                <c:out value="${item.quantity}" />x
                                                <c:out value="${item.productName}" />
                                                (<c:out value="${item.unitPrice}" /> SEK)
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>

        <div style="text-align: right; margin-top: 15px;">
            <button type="button" onclick="document.getElementById('ordersDialog').close()" style="padding: 6px 14px; cursor: pointer;">Close</button>
        </div>
    </dialog>
</body>
</html>