<%--
  Created by IntelliJ IDEA.
  User: ponur
  Date: 29.01.2020
  Time: 21:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<header class="page-header">
    <nav class="navbar navbar-expand-lg justify-content-around">
        <ul class="nav nounderline text-uppercase">
            <c:forEach items="${listOfDates}" var="date">
                <li class="nav-item ml-4">
                    <a class=
                       <c:choose>
                       <c:when test="${param.date == date}">
                               "nav-link color-header"
                    </c:when>
                    <c:otherwise>
                        "nav-link"
                    </c:otherwise>
                    </c:choose>
                    href="/view1?date=${date}&category=<c:if
                        test="${empty param.category}">Obiad</c:if>${param.category}">${date}</a>
                </li>
            </c:forEach>
            <li class="nav-item ml-4">
                <a class="nav-link" href="/view2">Lista zakupów</a>
            </li>
        </ul>
    </nav>
</header>
