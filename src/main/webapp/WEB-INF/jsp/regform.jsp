<%--
  Created by IntelliJ IDEA.
  User: kolokolov
  Date: 5/14/16
  Time: 6:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Registration form</title>
</head>
<body>
<h1>Registration form</h1>
<form:form method="post" modelAttribute="user" action="signUp">
    <table>
        <tr>
            <td>Nickname:</td>
            <td><form:input path="nickname" /></td>
            <td>3 symbols min</td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><form:password path="password" /></td>
            <td>6 digits min</td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Sign Up"></td>
        </tr>
    </table>
</form:form>
<a href="/">To main page</a>
</body>
</html>
