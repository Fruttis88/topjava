<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create or edit meal</title>
</head>
<body>
<form method="POST" action="meals" name="createOrUpdateMeal">
    id : <input type="text" readonly="readonly" name="id"
                value="<c:out value="${meal.id}"/>"/> <br/>
    description : <input type="text" name="description"
                         value="<c:out value="${meal.description}"/>"/> <br/>
    calories : <input type="text" name="calories"
                      value="<c:out value="${meal.calories}"/>"/> <br/>
    time : <input type="datetime-local" name="time"
                  value="<c:out value="${meal.dateTime}"/>"/> <br/>
    <input type="submit" value="Submit"/>
</form>
</body>

</html>
