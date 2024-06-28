<%--
  Created by IntelliJ IDEA.
  User: kimjiwoong
  Date: 2024. 6. 28.
  Time: 오후 12:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="Hello" method="get">
    <label for="inputText">Enter text for GET:</label>
    <input type="text" id="inputText" name="inputText">
    <input type="submit" value="Get">
</form>

<form action="Hello" method="post">
    <label for="inputTextPost">Enter text for POST:</label>
    <input type="text" id="inputTextPost" name="inputText">
    <input type="submit" value="Post">
</form>
</body>
</html>
