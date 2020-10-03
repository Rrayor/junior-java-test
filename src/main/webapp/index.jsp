
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); //HTTP 1.1
            response.setHeader("pragma", "no-cache"); //HTTP 1.0
            response.setHeader("Expires", "0"); //Proxies

            if (session.getAttribute("xml") != null) {
                response.sendRedirect("listview");
            }
        %>
        <title>Choose an xml file</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="style.css"/>
    </head>
    <body>
        <section class="container">

            <h1>Select a File</h1>

            <form action="add-xml" method="post">
                <div class="form-group">
                    <label for="xml">Choose an XML file</label>
                    <input type="text" name="xml" />
                    <small class="error">${messages.xml}</small>
                </div>
                <div class="form-group">
                    <button type="submit">Choose</button>
                </div>
            </form>

        </section>
    </body>
</html>
