<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
    <tr style="background-color: #3a87ad">
        <th>Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan=2>Action</th>
    </tr>
<c:forEach var="meals" items="${meals}">
    <tr style="background-color: <c:out value="${meals.exceed ? 'red' : 'green'}"/>">
        <td><c:out value="${TimeUtil.formatLocalDateTime(meals.dateTime)}"/></td>
        <td><c:out value="${meals.description}"/></td>
        <td><c:out value="${meals.calories}"/></td>
        <td><a style="color: yellow" href="meals?action=edit&mealId=<c:out value="${meals.id}"/>">Edit</a></td>
        <td><a style="color: yellow" href="meals?action=delete&mealId=<c:out value="${meals.id}"/>">Delete</a></td>
    </tr>
</c:forEach>
</table>
<p><a style="color: darkblue" href="meals?action=create">Create Meal</a></p>
</body>
</html>
