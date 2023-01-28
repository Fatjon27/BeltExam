<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. -->
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!-- Formatting (dates) -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/main.css"> <!-- change to match your file/naming structure -->
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/app.js"></script><!-- change to match your file/naming structure -->
</head>
<body>
<div class="container">
    <div class="d-flex justify-content-between">
        <h1>Namaste, ${user.name}!</h1>
        <a href="/logout">Logout</a>
    </div>
    <div>
        <p>Course Schedule</p>
        <table class="table table-striped table-bordered">
            <thead>
                <tr>
                    <th scope="col">Class Name</th>
                    <th scope="col">Instructor</th>
                    <th scope="col">Weekday</th>
                    <th scope="col">Price</th>
                    <th scope="col" class="text-primary">Time</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${courses}" var="course">
                    <tr>
                        <td><a href="/classes/${course.id}">${course.name}</a> /
                            <c:if test="${course.instructor.equals(user)}"><a href="/classes/${course.id}/edit">edit</a> </c:if> </td>
                        <td>${course.instructor.name}</td>
                        <td>${course.day}</td>
                        <td>$${course.price}</td>
                        <td class="text-primary">${course.time}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <%--@elvariable id="course" type="java"--%>
        <form:form action="/classes/new" method="get">
            <input type="submit" value="+ new course">
        </form:form>

    </div>
</div>
</body>
</html>