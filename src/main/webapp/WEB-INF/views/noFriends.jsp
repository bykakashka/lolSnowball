<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: byka
  Date: 20.12.18
  Time: 23.36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>No friends</title>
</head>
<body>
    There are no friends for you, bustard
    <br>
    <c:if test="${message != null}">
        ${message}
    </c:if>
</body>
</html>
