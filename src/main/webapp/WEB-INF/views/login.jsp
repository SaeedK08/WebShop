<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign In - WebShop</title>
</head>
<body>
    <h2>Sign In</h2>

    <!-- Display error message if authentication failed -->
    <c:if test="${not empty errorMessage}">
        <p style="color: red; font-weight: bold;">${errorMessage}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div>
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <br>
        <div>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <br>
        <button type="submit">Sign In</button>
    </form>

    <p>
        <a href="${pageContext.request.contextPath}/products">Back to products</a>
    </p>
</body>
</html>