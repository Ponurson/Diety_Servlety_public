<%--
  Created by IntelliJ IDEA.
  User: ponur
  Date: 29.01.2020
  Time: 21:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg justify-content-around" style="background-color: #5a615f">
    <ul class="nav nounderline text-uppercase">
        <li class="nav-item ml-4">
            <a class=
               <c:choose>
               <c:when test="${param.category == 'Śniadanie'}">
                       "nav-link color-header"
            </c:when>
            <c:otherwise>
                "nav-link"
            </c:otherwise>
            </c:choose>
            href="/view1?date=${param.date}&category=Śniadanie">Śniadanie</a>
        </li>
        <li class="nav-item ml-4">
            <a class=
               <c:choose>
               <c:when test="${param.category == 'Lunch'}">
                       "nav-link color-header"
            </c:when>
            <c:otherwise>
                "nav-link"
            </c:otherwise>
            </c:choose>
            href="/view1?date=${param.date}&category=Lunch">Lunch</a>
        </li>
        <li class="nav-item ml-4">
            <a class=
               <c:choose>
               <c:when test="${param.category == 'Obiad'}">
                       "nav-link color-header"
            </c:when>
            <c:otherwise>
                "nav-link"
            </c:otherwise>
            </c:choose>
            href="/view1?date=${param.date}&category=Obiad">Obiad</a>
        </li>
        <li class="nav-item ml-4">
            <a class=
               <c:choose>
               <c:when test="${param.category == 'II Śniadanie'}">
                       "nav-link color-header"
            </c:when>
            <c:otherwise>
                "nav-link"
            </c:otherwise>
            </c:choose>
            href="/view1?date=${param.date}&category=II Śniadanie">Kolacja</a>
        </li>
    </ul>
</nav>



