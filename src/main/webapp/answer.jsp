<%--
  Created by IntelliJ IDEA.
  User: zahar
  Date: 03.06.2022
  Time: 19:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Всі люди, які працюють в фірмі:
<table>
    <tr>
        <th>Name</th>
        <th>Surname</th>
    </tr>
    <c:forEach var="worker" items="${workers}">
        <tr>
            <td><c:out value="${worker.name}"/></td>
            <td><c:out value="${worker.surname}"/></td>
        </tr>
    </c:forEach>
</table>
Депертаменти де робітник має більшу зарплатню, ніж голова відділу:
<table>
    <tr>
        <th>Name</th>
    </tr>
    <c:forEach var="department" items="${departments}">
        <tr>
            <td><c:out value="${department.name}"/></td>
        </tr>
    </c:forEach>
</table>
Найбільша зарплата:
${max}
</body>
</html>
