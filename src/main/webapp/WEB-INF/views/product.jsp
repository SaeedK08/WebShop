<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Products - WebShop</title>
</head>
<body>
    <h1>Our Products</h1>

    <c:if test="${not empty sessionScope.currentUser}">
        <p>
            Signed in as: <strong><c:out value="${sessionScope.currentUser.username}" /></strong> |
            <a href="${pageContext.request.contextPath}/logout">Sign Out</a>
        </p>
    </c:if>

    <c:if test="${not empty sessionScope.cartError}">
        <p style="color: red; font-weight: bold;">
            <c:out value="${sessionScope.cartError}" />
        </p>
        <c:remove var="cartError" scope="session" />
    </c:if>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
            <tr>
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

    <p style="margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/cart">View Shopping Cart</a>
    </p>
</body>
</html>