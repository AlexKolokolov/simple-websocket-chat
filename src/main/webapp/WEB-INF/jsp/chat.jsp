<%--
  Created by IntelliJ IDEA.
  User: kolokolov
  Date: 5/14/16
  Time: 6:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Chat</title>
        <spring:url value="resources/js/chat.js" var="chatJs" />
        <spring:url value="resources/css/style.css" var="styleCss" />
        <link rel="stylesheet" type="text/css" href="${styleCss}" />

        <script type="application/javascript" >
            var nickname = '${user.nickname}';
        </script>
        <script type="application/javascript" src="${chatJs}"></script>

    </head>
    <body>
        <h2>Hello, ${user.nickname}!</h2>
        <div class="noscript"><h2>Seems your browser doesn't support Javascript! Websockets rely on
            Javascript being enabled. Please enable
            Javascript and reload this page!</h2>
        </div>
        <div>
            <div id="console-container">
                <div id="console"/>
            </div>
            <p>
                <input type="text" placeholder="type and press enter to chat" id="chat"/>
            </p>
        </div>
    </body>
</html>
