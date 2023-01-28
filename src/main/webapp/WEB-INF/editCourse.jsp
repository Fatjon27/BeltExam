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
    <title>Edit</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/main.css"> <!-- change to match your file/naming structure -->
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/app.js"></script><!-- change to match your file/naming structure -->
</head>
<body>
<h1>${course.name}</h1>
<%--@elvariable id="course" type="java"--%>
<form:form action="/classes/${course.id}/edit" method="put" modelAttribute="course">
    <p>
        <form:label path="name">Name:</form:label>
        <form:errors path="name" class="text-danger"/>
        <form:input path="name"/>
    </p>
    <p>
        <form:label path="day">Day of the week:</form:label>
        <form:errors path="day" class="text-danger"/>
        <form:input path="day"/>
    </p>
    <p>
        <form:label path="price">Drop-in price:</form:label>
        <form:errors path="price" class="text-danger"/>
        <form:input path="price" type="number"/>
    </p>
            <p>
                <form:label path="time">Time:</form:label>
                <form:errors path="time" class="text-danger"/>
                <form:input path="time" type="time"/>
            </p>
    <p>
        <form:label path="description">Description:</form:label>
        <form:errors path="description" class="text-danger"/>
        <form:textarea path="description"/>
    </p>
    <input type="submit" value="Submit" >
</form:form>
<hr>
<form:form action="/courses/${course.id}/delete" method="delete">
    <input type="submit" value="Delete" class="bg-danger text-light">
</form:form>
<hr>
<form method="get" action="/dashboard">
    <input type="submit" value="Cancel">
</form>

</body>
</html>