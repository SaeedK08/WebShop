<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Produkter</title>
</head>
<body>
    <h1>Våra Produkter</h1>
    <table border="1">
        <thead>
            <tr>
                <th>Namn</th>
                <th>Beskrivning</th>
                <th>Pris</th>
                <th>Status och Köp</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="p" items="${products}">
                <tr>
                    <td>${p.name}</td>
                    <td>${p.description}</td>
                    <td>${p.price} kr</td>
                    <td>
                        <c:choose>
                            <c:when test="${p.inStock}">
                                <form action="${pageContext.request.contextPath}/cart" method="post">
                                    <input type="hidden" name="productId" value="${p.id}">
                                    <input type="hidden" name="action" value="add">
                                    <input type="number" name="quantity" value="1" min="1" max="${p.stock}" style="width: 50px;">
                                    <button type="submit">Köp</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                Slut i lager
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <br>
    <a href="${pageContext.request.contextPath}/cart">Gå till kundvagn</a>
</body>
</html>