<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<script>
    function validateForm() {
        var deptno = document.getElementById('deptno').value;
        var dname = document.getElementById('dname').value;
        var loc = document.getElementById('loc').value;

        if (deptno === "" || dname === "" || loc === "") {
            alert('모든 필드를 채워주세요.');
            return false;
        }
        return true;
    }
</script>
<form action="dept_insert_form_ok.jsp" method="post" onsubmit="return validateForm()">
<fieldset>
<label for="deptno">부서 번호</label>
<input type="text" id="deptno" name="deptno" />

<br/><br/>

<label for="dname">부서 이름</label>
<input type="text" id="dname" name="dname" />

<br/><br/>

<label for="loc">부서위치</label>
<input type="text" id="loc" name="loc" />
<br/><br/>

<input type="submit" id="btn" value="내용입력" />
</fieldset>


</form>

</body>
</html>