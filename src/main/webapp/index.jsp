<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome - WebShop</title>
</head>
<body>
    <h1>Welcome to WebShop</h1>

    <c:choose>
        <c:when test="${not empty sessionScope.currentUser}">
            <p>Signed in as: <strong><c:out value="${sessionScope.currentUser.username}" /></strong> (${sessionScope.currentUser.role})</p>
            <p>
                <a href="${pageContext.request.contextPath}/products">Browse Products</a> |
                <a href="${pageContext.request.contextPath}/logout">Sign Out</a>
            </p>
        </c:when>
        <c:otherwise>
            <p>Please sign in to view our catalog.</p>
            <p>
                <a href="${pageContext.request.contextPath}/login">Sign In</a>
            </p>
        </c:otherwise>
    </c:choose>
</body>
</html>