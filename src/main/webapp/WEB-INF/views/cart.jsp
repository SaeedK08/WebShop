<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Your Shopping Cart - WebShop</title>
</head>
<body>
    <h1>Your Shopping Cart</h1>

    <c:if test="${not empty sessionScope.cartError}">
        <p style="color: red; font-weight: bold;">
            <c:out value="${sessionScope.cartError}" />
        </p>
        <c:remove var="cartError" scope="session" />
    </c:if>

    <c:choose>
        <c:when test="${empty cartInfo or empty cartInfo.items}">
            <p>Your shopping cart is empty.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="8" cellspacing="0">
                <thead>
                    <tr>
                        <th>Product</th>
                        <th>Price/Unit</th>
                        <th>Quantity</th>
                        <th>Subtotal</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${cartInfo.items}">
                        <tr>
                            <td><c:out value="${item.productName}" /></td>
                            <td><c:out value="${item.price}" /> SEK</td>
                            <td><c:out value="${item.quantity}" /></td>
                            <td><c:out value="${item.totalPrice}" /> SEK</td>
                            <td>
                                <form action="${pageContext.request.contextPath}/cart" method="post" style="margin: 0;">
                                    <input type="hidden" name="productId" value="${item.productId}">
                                    <input type="hidden" name="action" value="remove">
                                    <button type="submit">Remove</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <h3>Total: <c:out value="${cartInfo.totalCartPrice}" /> SEK</h3>
            <form action="${pageContext.request.contextPath}/checkout" method="post" style="margin-top: 15px;">
                <button type="submit" style="padding: 8px 16px; font-weight: bold; cursor: pointer;">
                    Proceed to Checkout
                </button>
            </form>
        </c:otherwise>
    </c:choose>

    <p style="margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/products">Continue Shopping</a>
    </p>
</body>
</html>