<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="javax.naming.NamingException" %>
<%@ page import="java.sql.SQLException" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("utf-8");
    String seq = request.getParameter("seq");
    String password = request.getParameter("password");
    String subject = request.getParameter("subject");
    String content = request.getParameter("content");

    System.out.println(seq);
    System.out.println(password);
    System.out.println(subject);
    System.out.println(content);
    PreparedStatement pstmt = null;
    Connection conn= null;
    // 0 정상 1 비번 틀림 2 에러
    int flag = 1;
    try {
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        DataSource dataSource = (DataSource) envCtx.lookup("jdbc/mariadb1");

        conn = dataSource.getConnection();
//        UPDATE board1 SET subject = '새로운 제목', content = '새로운 내용' WHERE seq = '특정시퀀스' AND password = '특정비밀번호';
//        String sql = "DELETE FROM board1 WHERE seq = ? and password = password(?)";
        String sql = "UPDATE board1 SET subject = ?, content = ? WHERE seq =? AND password = password(?)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, subject);
        pstmt.setString(2, content);
        pstmt.setString(3, seq);
        pstmt.setString(4, password);
        int result = pstmt.executeUpdate();

        if (result == 0) {
            flag = 1;
        }else if(result == 1){
            flag = 0;
        }else {
            flag = 2;
        }
    }
    catch (NamingException e) {
        e.printStackTrace();
    }catch (SQLException e) {
        e.printStackTrace();
    }finally {
        if(pstmt != null) {
            pstmt.close();
        }
        if(conn != null) {
            conn.close();
        }
    }
    out.println("<script type='text/javascript'>");
    if(flag==0){
        System.out.println("정상");
        out.println("alert('글 수정 성공');");
        out.println("location.href='./board_list1.jsp?seq='"+ seq + ";");
    }
    else if(flag==1){
        out.println("alert('비번 오류');");
        out.println("history.back();");
    }else {
        out.println("alert('글 수정 실패');");
        out.println("history.back();");
    }
    out.println("</script>");
%>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
