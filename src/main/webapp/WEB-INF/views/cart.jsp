<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Your Shopping Cart - WebShop</title>
</head>
<body style="font-family: sans-serif; padding: 20px;">
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
            <table border="1" cellpadding="8" cellspacing="0" style="border-collapse: collapse; width: 100%; max-width: 800px;">
                <thead>
                    <tr style="background-color: #f2f2f2;">
                        <th>Product</th>
                        <th>Price/Unit</th>
                        <th>Quantity</th>
                        <th>Subtotal</th>
                        <th></th> <!-- Tom rubrik för Remove-knappen -->
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${cartInfo.items}">
                        <tr>
                            <td><strong><c:out value="${item.productName}" /></strong></td>
                            <td><c:out value="${item.price}" /> SEK</td>

                            <!-- Kolumn 3: Quantity (Inmatningsfältet med auto-submit) -->
                            <td align="center">
                                <form action="${pageContext.request.contextPath}/cart" method="post" style="margin: 0;">
                                    <input type="hidden" name="action" value="update" />
                                    <input type="hidden" name="productId" value="${item.productId}" />
                                    <input type="number" name="quantity" value="${item.quantity}" min="1"
                                           style="width: 60px; text-align: center; padding: 4px;" onchange="this.form.submit()" />
                                </form>
                            </td>

                            <!-- Kolumn 4: Subtotal -->
                            <td><c:out value="${item.totalPrice}" /> SEK</td>

                            <!-- Kolumn 5: Stylad Remove-knapp -->
                            <td align="center">
                                <form action="${pageContext.request.contextPath}/cart" method="post" style="margin: 0;">
                                    <input type="hidden" name="action" value="remove" />
                                    <input type="hidden" name="productId" value="${item.productId}" />
                                    <button type="submit" style="background-color: #dc3545; color: white; border: none; padding: 6px 12px; border-radius: 4px; cursor: pointer; font-weight: bold; font-size: 0.9em;">
                                        Remove
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <h3 style="margin-top: 20px;">Total: <c:out value="${cartInfo.totalCartPrice}" /> SEK</h3>

            <form action="${pageContext.request.contextPath}/checkout" method="post" style="margin-top: 15px;">
                <button type="submit" style="background-color: #28a745; color: white; border: none; padding: 10px 20px; font-weight: bold; font-size: 1em; border-radius: 4px; cursor: pointer;">
                    Proceed to Checkout
                </button>
            </form>
        </c:otherwise>
    </c:choose>

    <p style="margin-top: 30px;">
        <a href="${pageContext.request.contextPath}/products" style="text-decoration: none; color: #007bff; font-weight: bold;">
            ← Continue Shopping
        </a>
    </p>
</body>
</html>