<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Staff Warehouse - Order Packing</title>
</head>
<body style="font-family: sans-serif; padding: 20px;">

    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 15px;">
        <a href="${pageContext.request.contextPath}/products"
           style="text-decoration: none; font-weight: bold; padding: 6px 12px; background-color: #eee; border: 1px solid #ccc; border-radius: 4px; color: #333;">
            ← Back to Store
        </a>
        <span>Logged in as Staff: <strong><c:out value="${sessionScope.currentUser.username}" /></strong></span>
    </div>

    <hr/>

    <h2>Warehouse: Order Packing Management</h2>

    <c:if test="${not empty sessionScope.staffSuccess}">
        <p style="color: green; font-weight: bold;"><c:out value="${sessionScope.staffSuccess}" /></p>
        <c:remove var="staffSuccess" scope="session" />
    </c:if>

    <c:if test="${not empty sessionScope.staffError}">
        <p style="color: red; font-weight: bold;"><c:out value="${sessionScope.staffError}" /></p>
        <c:remove var="staffError" scope="session" />
    </c:if>

    <c:choose>
        <c:when test="${empty orders}">
            <p>No orders found in the database.</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="8" cellspacing="0" style="border-collapse: collapse; width: 100%;">
                <tr style="background-color: #f2f2f2;">
                    <th>Order #</th>
                    <th>Customer</th>
                    <th>Date</th>
                    <th>Total</th>
                    <th>Status</th>
                    <th>Items to Pack</th>
                    <th>Action</th>
                </tr>
                <c:forEach var="o" items="${orders}">
                    <tr>
                        <td align="center"><strong>#<c:out value="${o.id()}" /></strong></td>
                        <td><c:out value="${o.username()}" /></td>
                        <td><c:out value="${o.orderDate()}" /></td>
                        <td><c:out value="${o.totalPrice()}" /> SEK</td>
                        <td align="center">
                            <span style="padding: 3px 8px; border-radius: 4px; font-weight: bold;
                                  background-color: ${o.status() == 'PENDING' ? '#fff3cd' : '#d4edda'};
                                  color: ${o.status() == 'PENDING' ? '#856404' : '#155724'};">
                                <c:out value="${o.status()}" />
                            </span>
                        </td>
                        <td>
                            <ul style="margin: 0; padding-left: 20px;">
                                <c:forEach var="item" items="${o.items()}">
                                    <li>
                                        <strong><c:out value="${item.quantity()}" />x</strong>
                                        <c:out value="${item.productName()}" />
                                        <em>(<c:out value="${item.unitPrice()}" /> SEK/ea)</em>
                                    </li>
                                </c:forEach>
                            </ul>
                        </td>
                        <td align="center">
                            <c:choose>
                                <c:when test="${o.status() == 'PENDING'}">
                                    <form action="${pageContext.request.contextPath}/staff" method="post" style="margin: 0;">
                                        <input type="hidden" name="action" value="packOrder" />
                                        <input type="hidden" name="orderId" value="${o.id()}" />
                                        <button type="submit" style="background-color: #28a745; color: white; border: none; padding: 6px 12px; border-radius: 4px; cursor: pointer; font-weight: bold;">
                                            Pack Order
                                        </button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <span style="color: gray;">Packed</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>

</body>
</html>