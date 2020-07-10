<%--
  Created by IntelliJ IDEA.
  User: ponur
  Date: 18.02.2020
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar">
    <div class="dropdown">
        <button class="dropbtn" onclick="myFunction1()">Lista zakupów
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-content" id="dates">
            <c:forEach items="${listOfDates}" var="date">
                <a href="/view1?date=${date}&category=<c:if test="${empty param.category}">Obiad</c:if>${param.category}">${date}</a>
            </c:forEach>
            <a href="/view2">Lista zakupów</a>
        </div>
    </div>
    <a style="float: right" href="" onclick="document.getElementById('shopping_list').submit();return false;">Aktualizuj
        Listę</a>
</div>
<script type="text/javascript">
    /* When the user clicks on the button,
    toggle between hiding and showing the dropdown content */
    function myFunction1() {
        document.getElementById("dates").classList.toggle("show");
    }

    // Close the dropdown if the user clicks outside of it
    window.onclick = function (e) {
        if (!e.target.matches('.dropbtn')) {
            var myDropdown = document.getElementById("dates");
            if (myDropdown.classList.contains('show')) {
                myDropdown.classList.remove('show');
            }
        }
    }
</script>
