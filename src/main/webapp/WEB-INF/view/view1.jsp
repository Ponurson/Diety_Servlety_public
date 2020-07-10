<%--
  Created by IntelliJ IDEA.
  User: ponur
  Date: 29.01.2020
  Time: 21:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<%@ include file="/WEB-INF/fragments/header2.jsp" %>
<div class="swiper-container">
    <div class="swiper-wrapper">
        <c:forEach items="${dataForSwiper}" var="singleDish">
            <div class="swiper-slide">
                <div class="main-div"
                        <c:if test="${singleDish.isInCart == 'ok'}">
                            style="background-color: #97dbc6"
                        </c:if>
                >
                        ${singleDish.data}<br>
                        ${singleDish.name}<br>
                    <c:if test="${param.category != 'Śniadanie' &&
                     singleDish.name != 'Posiłek pominięty	' &&
                     sessionScope.username == 'admin'}">
                        <form action="/view1" method="post">
                            <button style="z-index: 3" type="submit" name="skip"
                                    value=${singleDish.data},${param.category}>pomiń posiłek
                            </button>
                        </form>
                    </c:if><br>
                    <c:if test="${param.category == 'Śniadanie' or
                     singleDish.name == 'Posiłek pominięty	' or
                     sessionScope.username != 'admin'}"><br><br></c:if>
                    <%@ include file="/WEB-INF/fragments/list.jsp" %>
                    <br>
                    <%@ include file="/WEB-INF/fragments/recipe.jsp" %>
                    <br>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<script src="https://unpkg.com/swiper/js/swiper.js"></script>
<script src="https://unpkg.com/swiper/js/swiper.min.js"></script>
<script>
    var mySwiper = new Swiper('.swiper-container', {
        // Optional parameters
        direction: 'horizontal',
        loop: true,
        initialSlide: ${centerSlide},
        effect: "cube",
        speed: 300,
    })
</script>
</body>
</html>
