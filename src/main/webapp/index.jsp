<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ponur
  Date: 29.01.2020
  Time: 20:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Zaplanuj Jedzonko</title>
    <link href='<c:url value="/css/style2.css"/>' rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<div class="main-div" style="margin-top: 50%; text-align:center;">
    <c:if test="${not empty wrongLogin}">
        ${wrongLogin}
    </c:if>
    <form action="/login" method="post">
        <input type="text" name="username"/>
        <input name="password" type="password"/>
        <button type="submit">Zaloguj</button>
    </form>
</div>
</body>
</html>
