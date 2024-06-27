<%--
  Created by IntelliJ IDEA.
  User: kimjiwoong
  Date: 2024. 6. 27.
  Time: 오후 12:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/sql" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <s:setDataSource var="ds"
                   url="jdbc:mariadb://localhost/sample"
                   driver="org.mariadb.jdbc.Driver"
                   user="root"
                   password="1234"/>
출력 : ${ds}
<s:update
        var="result"
        dataSource="${ds}"
        sql = "INSERT INTO dept VALUES (60,'개발부', '서울');"
        />
</body>
</html>
