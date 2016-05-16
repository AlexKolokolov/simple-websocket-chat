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
    <title>Welcome</title>
</head>
<body>
<h1>Chat</h1>
<form:form method="post" modelAttribute="user" action="/chat">
    <table>
        <tr>
            <td>Nickname:</td>
            <td><form:input path="nickname" /></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><form:input path="password" /></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Chat"></td>
        </tr>
    </table>
</form:form>
</body>
</html>
