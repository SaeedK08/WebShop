<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Products - WebShop</title>
</head>
<body>
    <!-- Användarmeny & Navigering -->
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px;">
        <div>
            <c:choose>
                <c:when test="${not empty sessionScope.currentUser}">
                    <span>Signed in as: <strong><c:out value="${sessionScope.currentUser.username}" /></strong> (<c:out value="${sessionScope.currentUser.role}" />)</span>

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
</body>
</html>