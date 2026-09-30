<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin - User Management</title>
</head>
<body>
    <!-- Top-navigering -->
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px;">
        <a href="${pageContext.request.contextPath}/products"
           style="text-decoration: none; font-weight: bold; padding: 6px 12px; background-color: #eee; border: 1px solid #ccc; border-radius: 4px; color: #333;">
            ← Back to Products
        </a>
        <span>Signed in as Admin: <strong><c:out value="${sessionScope.currentUser.username}" /></strong></span>
    </div>

    <hr/>

    <h1>User Administration</h1>

    <c:if test="${not empty sessionScope.adminSuccess}">
        <p style="color: green; font-weight: bold;"><c:out value="${sessionScope.adminSuccess}" /></p>
        <c:remove var="adminSuccess" scope="session" />
    </c:if>

    <c:if test="${not empty sessionScope.adminError}">
        <p style="color: red; font-weight: bold;"><c:out value="${sessionScope.adminError}" /></p>
        <c:remove var="adminError" scope="session" />
    </c:if>

    <table border="1" cellpadding="8" cellspacing="0" style="border-collapse: collapse; width: 100%; margin-top: 15px;">
        <thead>
            <tr style="background-color: #f2f2f2;">
                <th>ID</th>
                <th>Username</th>
                <th>Current Role</th>
                <th>Change Role</th>
                <th>Change Password</th>
                <th>Delete</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="u" items="${usersInfo}">
                <tr>
                    <td><c:out value="${u.id}" /></td>
                    <td><c:out value="${u.username}" /></td>
                    <td><strong><c:out value="${u.role}" /></strong></td>

                    <!-- Ändra roll -->
                    <td>
                        <form action="${pageContext.request.contextPath}/admin" method="post" style="display:inline; margin: 0;">
                            <input type="hidden" name="action" value="changeRole" />
                            <input type="hidden" name="userId" value="${u.id}" />
                            <select name="role">
                                <option value="CUSTOMER" ${u.role == 'CUSTOMER' ? 'selected' : ''}>CUSTOMER</option>
                                <option value="STAFF" ${u.role == 'STAFF' ? 'selected' : ''}>STAFF</option>
                                <option value="ADMIN" ${u.role == 'ADMIN' ? 'selected' : ''}>ADMIN</option>
                            </select>
                            <button type="submit">Update</button>
                        </form>
                    </td>

                    <!-- Ändra lösenord -->
                    <td>
                        <form action="${pageContext.request.contextPath}/admin" method="post" style="display:inline; margin: 0;">
                            <input type="hidden" name="action" value="changePassword" />
                            <input type="hidden" name="userId" value="${u.id}" />
                            <input type="password" name="password" placeholder="New password" required style="width: 120px;" />
                            <button type="submit">Set</button>
                        </form>
                    </td>

                    <!-- Radera användare -->
                    <td>
                        <c:choose>
                            <c:when test="${u.id == sessionScope.currentUser.id}">
                                <em>(Current User)</em>
                            </c:when>
                            <c:otherwise>
                                <form action="${pageContext.request.contextPath}/admin" method="post" style="display:inline; margin: 0;"
                                      onsubmit="return confirm('Are you sure you want to delete user ${u.username}?');">
                                    <input type="hidden" name="action" value="delete" />
                                    <input type="hidden" name="userId" value="${u.id}" />
                                    <button type="submit" style="color: red; cursor: pointer;">Delete</button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>