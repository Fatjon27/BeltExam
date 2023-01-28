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
    <title>Create a Task</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/main.css"> <!-- change to match your file/naming structure -->
    <script src="/webjars/jquery/jquery.min.js"></script>
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/app.js"></script><!-- change to match your file/naming structure -->
</head>
<body>
    <a href="/dashboard">Dashboard</a>
    <hr>
    <h1>${course.name} with ${course.instructor.name}</h1>
    <h2>Day: ${course.day}</h2>
    <h2>Cost: ${course.price}</h2>
    <h2 class="text-primary">Time: ${course.time}</h2>
    <p>Description: ${course.description}</p>
    <hr>
    <h3 class="text-primary">Students</h3>
    <c:forEach items="${students}" var="student">
        <h3 class="text-primary">${student.name} - ${student.email}</h3>
    </c:forEach>
    <h2 class="text-primary">Add Students to Course</h2>
    <%--@elvariable id="student" type="java"--%>
    <form:form method="post" action="/students/${course.id}/new" modelAttribute="student">
        <div class="text-primary">
            <form:errors path="name" class="errors text-danger"></form:errors>
            <form:label path="name">Student Name: </form:label>
            <form:input class="form-control" type="text" path="name"></form:input>
        </div>
        <div class="text-primary">
            <form:errors path="email" class="errors text-danger"></form:errors>
            <form:label path="email">Email: </form:label>
            <form:input class="form-control" type="text" path="email"></form:input>
        </div>
        <input type="submit" value="Add Student" class="bg-primary text-light">
    </form:form>
    <form method="post" action="/assign/${course.id}">
        <label class="text-primary">Choose Student</label>
        <select name="studentId" id="studentId">
            <c:forEach items="${notYourStudents}" var="student">
                <option value="${student.id}">${student.name} - ${student.email}</option>
            </c:forEach>
        </select>
        <input type="submit" value="Add Student">
    </form>
</body>
</html>