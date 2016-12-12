<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
    <tr style="background-color: azure">
        <th>Time</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
<c:forEach var="list" items="${list}">
    <tr style="background-color: <c:out value="${list.exceed ? 'red' : 'green'}"/>">
        <td><c:out value="${TimeUtil.formatLocalDateTime(list.dateTime)}"/></td>
        <th>${list.description}</th>
        <th>${list.calories}</th>
    </tr>
</c:forEach>
</table>
</body>
</html>
