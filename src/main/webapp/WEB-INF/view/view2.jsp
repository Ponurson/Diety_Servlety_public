<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ponur
  Date: 06.02.2020
  Time: 14:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>input[type=checkbox]:checked + label.strikethrough {
        text-decoration: line-through;
    }</style>
    <title>Diety_Bejbe</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href='<c:url value="/css/style2.css"/>' rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<%@ include file="/WEB-INF/fragments/header_lista_zakupów.jsp" %>
<div class="main-div">
    Lista aktualna do: ${doKiedy}<br>
    <form id="shopping_list" action="/view21" method="post">
        <c:forEach items="${listaZakupow}" var="zakup">
            <input type="checkbox" name="bought" value="${zakup.id}" id="${zakup.name}"
                   <c:if test="${zakup.bought}">checked</c:if>><label class="strikethrough"
                                                                      for="${zakup.name}">${zakup.amount}
            g ${zakup.altMass} ${zakup.name}</label><br>
        </c:forEach>
    </form>
    <button id="showForm">utwórz nową listę zakupów</button>
    <form id="createShoppingList" style="display : none" action="/view2" method="post">
        <div style="height: 200px; overflow: scroll;">
            <table style="font-size: 50%">
                <tr>
                    <td>data</td>
                    <td>śniadanie</td>
                    <td>lunch</td>
                    <td>obiad</td>
                    <td>kolacja</td>
                </tr>
                <c:forEach items="${daniaForZakupy}" var="row" varStatus="id1">
                    <tr>
                        <td>${row[0].data}</td>
                        <c:forEach items="${row}" var="item" varStatus="id2">
                            <td>
                                <input type="checkbox" name="shoppingCart"
                                       value="${item.group};${item.cat};${item.data};${item.id}"
                                       id="${id1.index*4+id2.index}">
                                <label for="${id1.index*4+id2.index}">${item.name}</label>
                            </td>
                        </c:forEach>
                    </tr>
                </c:forEach>

            </table>
        </div>
        <button type="submit" name="nowaLista">wyślij</button>
    </form>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="/js/app.js"></script>
</body>
</html>
