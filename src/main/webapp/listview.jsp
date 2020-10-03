<%--
    Document   : listview
    Created on : Sep 21, 2020, 11:50:20 AM
    Author     : simon
--%>

<%@page import="com.benjaminsimon.testconsole.FilterAndOrder"%>
<%@page import="com.benjaminsimon.testconsole.TextList.Order"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <%
            FilterAndOrder filterAndOrder = (FilterAndOrder) request.getAttribute("filterAndOrder");

            String search = "";
            Order order = Order.NAME;
            boolean reverse = false;

            if (filterAndOrder != null) {
                search = filterAndOrder.getFilter() != null ? filterAndOrder.getFilter() : "";
                order = filterAndOrder.getOrder();
                reverse = filterAndOrder.isReverse();
            }
        %>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="style.css"/>
        <title>Names</title>
    </head>
    <body>
        <h1>Names</h1>

        <section class="container">
            <form action="listview" method="get">
                <div class="form-group">
                    <label for="search">Search:</label>
                    <input type="text" name="search" placeholder="Search..." value="<%= search%>"/>
                </div>
                <div class="form-group">
                    <label for="order">Order By:</label>
                    <select name="order">
                        <option value="n" <%= order == Order.NAME ? "selected" : ""%>>Name</option>
                        <option value="f" <%= order == Order.FREQUENCY ? "selected" : ""%>>Frequency</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="rev">Order</label>
                    <select name="rev">
                        <option value="_" <%= !reverse ? "selected" : ""%>>Ascending</option>
                        <option value="rev" <%= reverse ? "selected" : ""%>>Descending</option>
                    </select>
                </div>
                <div class="form-group">
                    <button type="submit">Filter</button>
                </div>
            </form>

            <section class="list-container">
                <h1>${list.size()} items were found</h1>
                <c:forEach items="${list}" var="listItem">
                    <p>${listItem}</p>
                </c:forEach>
            </section>
        </section>
    </body>
</html>
