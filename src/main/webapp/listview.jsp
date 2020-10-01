<%--
    Document   : listview
    Created on : Sep 21, 2020, 11:50:20 AM
    Author     : simon
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
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
                    <input type="text" name="search" placeholder="Search..." />
                </div>
                <div class="form-group">
                    <label for="order">Order By:</label>
                    <select name="order">
                        <option value="n">Name</option>
                        <option value="f">Frequency</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="rev">Order</label>
                    <select name="rev">
                        <option value="_">Ascending</option>
                        <option value="rev">Descending</option>
                    </select>
                </div>
                <div class="form-group">
                    <button type="submit">Filter</button>
                </div>
            </form>

            <section class="list-container">
                <h1>${names.size()} items were found</h1>
                <c:forEach items="${names}" var="name">
                    <p>${name}</p>
                </c:forEach>
            </section>
        </section>
    </body>
</html>
