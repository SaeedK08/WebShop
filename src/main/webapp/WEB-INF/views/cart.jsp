<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Din Kundvagn</title>
</head>
<body>
    <h1>Din Kundvagn</h1>

    <c:choose>
        <c:when test="${empty cartInfo.items}">
            <p>Din kundvagn är tom.</p>
        </c:when>
        <c:otherwise>
            <table border="1">
                <thead>
                    <tr>
                        <th>Produkt</th>
                        <th>Pris/st</th>
                        <th>Antal</th>
                        <th>Totalt</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${cartInfo.items}">
                        <tr>
                            <td>${item.productName}</td>
                            <td>${item.price} kr</td>
                            <td>${item.quantity}</td>
                            <td>${item.totalPrice} kr</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <h3>Totalsumma: ${cartInfo.totalCartPrice} kr</h3>
        </c:otherwise>
    </c:choose>

    <br>
    <a href="${pageContext.request.contextPath}/products">Fortsätt handla</a>
</body>
</html>