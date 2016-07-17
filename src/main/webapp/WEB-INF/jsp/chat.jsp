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
        <meta http-equiv="Cache-Control" content="no-cache, must-revalidate, private, no-store, s-maxage=0, max-age=0" />
        <meta http-equiv="Pragma" content="no-cache" />
        <title>Chat</title>
        <spring:url value="resources/js/jquery-3.1.0.min.js" var="jQuery" />
        <spring:url value="resources/js/chat.js" var="chatJs" />
        <script type="application/javascript" src="${jQuery}"></script>
        <script type="application/javascript" src="${chatJs}"></script>
        <spring:url value="resources/css/style.css" var="styleCss" />
        <link rel="stylesheet" type="text/css" href="${styleCss}" />

    </head>
    <body>
        <div id="main">
            <div id="header">
                <h2>Welcome to chat, ${user.nickname}!</h2>
                <a href="/logout" rel="Log out"><button>Log out</button></a>
            </div>
            <div class="noscript">
                <h2>Seems your browser doesn't support Javascript!
                Websockets rely on Javascript being enabled.
                Please enable Javascript and reload this page!</h2>
            </div>
            <div id="console-container">
                <div id="console"></div>
            </div>
            <div>
                <input type="text" placeholder="type and press enter to chat" id="chat"/>
            </div>
        </div>
        <div id="aside">
            <h3>Users on-line</h3>
            <div id="loggedUsers"></div>
        </div>
    </body>
</html>