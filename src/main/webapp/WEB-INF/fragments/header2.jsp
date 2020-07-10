<%--
  Created by IntelliJ IDEA.
  User: ponur
  Date: 18.02.2020
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar">
    <div class="dropdown">
        <button class="dropbtn" onclick="myFunction1()">${param.date}
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-content" id="dates">
            <a href="/view2">Lista zakupów</a>
            <c:forEach items="${listOfDates}" var="date">
                <a href="/view1?date=${date}&category=<c:if test="${empty param.category}">Obiad</c:if>${param.category}">${date}</a>
            </c:forEach>
        </div>
    </div>
    <div class="dropdown" style="float: right">
        <button class="dropbtn" onclick="myFunction2()">
            <c:choose>
                <c:when test="${param.category == 'II Śniadanie'}">
                    Kolacja
                </c:when>
                <c:otherwise>
                    ${param.category}
                </c:otherwise>
            </c:choose>
            <i class="fa fa-caret-down"></i>
        </button>
        <div class="dropdown-content" id="categories">
            <a href="/view1?date=${param.date}&category=Śniadanie">Śniadanie</a>
            <a href="/view1?date=${param.date}&category=Lunch">Lunch</a>
            <a href="/view1?date=${param.date}&category=Obiad">Obiad</a>
            <a href="/view1?date=${param.date}&category=II Śniadanie">Kolacja</a>
        </div>
    </div>
</div>
<script type="text/javascript">
    /* When the user clicks on the button,
    toggle between hiding and showing the dropdown content */
    function myFunction1() {
        document.getElementById("dates").classList.toggle("show");
    }

    function myFunction2() {
        document.getElementById("categories").classList.toggle("show");
    }

    // Close the dropdown if the user clicks outside of it
    window.onclick = function (e) {
        if (!e.target.matches('.dropbtn')) {
            var myDropdown = document.getElementById("dates");
            if (myDropdown.classList.contains('show')) {
                myDropdown.classList.remove('show');
            }
            var myDropdown = document.getElementById("categories");
            if (myDropdown.classList.contains('show')) {
                myDropdown.classList.remove('show');
            }
        }
    }
</script>

