<%@ page import="java.lang.reflect.Member" %><%--
  Created by IntelliJ IDEA.
  User: kimjiwoong
  Date: 2024. 6. 20.
  Time: 오전 9:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="pack1.MemberTo" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    Hello JSP
    <%
        MemberTo memberTo = new MemberTo();
        memberTo.setName("test");
    %>
</body>
</html>
