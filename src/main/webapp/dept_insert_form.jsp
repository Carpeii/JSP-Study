<%--
  Created by IntelliJ IDEA.
  User: kimjiwoong
  Date: 2024. 6. 14.
  Time: 오후 2:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <!--dept_insert_form-->
<fieldset>
    <label for="deptno">부서번호</label>
    <input type="text" id="deptno" name="deptno"/>

    <label for="dname">부서이름</label>
    <input type="text" id="dname" name="dname"/>

    <label for="loc">부서위치</label>
    <input type="text" id="loc" name="loc"/>

    <br/><br/>
    <input type="button" id="btn" value="내용 입력"/>
</fieldset>
</body>
</html>
