<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ponur
  Date: 25.03.2020
  Time: 11:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Diety_Bejbe</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href='<c:url value="/css/style2.css"/>' rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://unpkg.com/swiper/css/swiper.css">
    <link rel="stylesheet" href="https://unpkg.com/swiper/css/swiper.min.css">
</head>
<body>
<div class="main-div" style="margin-top: 10%; text-align:center;">
    <form action="/register" method="post">
        <h1>Rejestracja</h1>
        <div>
            <input type="text" id="name" name="username" placeholder="podaj nazwę użytkownika">
        </div>
        <div>
            <input type="password" id="password" name="password" placeholder="podaj hasło">
        </div>
        <div>
            <input type="text" id="sniadanie" name="sniadanie" placeholder="dla ilu osób śniadania">
        </div>
        <div>
            <input type="text" id="lunch" name="lunch" placeholder="dla ilu osób lunche">
        </div>
        <div>
            <input type="text" id="obiad" name="obiad" placeholder="dla ilu osób obiady">
        </div>
        <div>
            <input type="text" id="kolacja" name="kolacja" placeholder="dla ilu osób kolacje">
        </div>
        <div>
            <input type="text" id="ileDniPodRzad" name="ileDniPodRzad" placeholder="ile dni z rzędu to samo">
        </div>
        <button type="submit">Zarejestruj</button>
    </form>
</div>
</body>
</html>
