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
                <th>Status</th>
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
                                Finns i lager
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
</body>
</html>