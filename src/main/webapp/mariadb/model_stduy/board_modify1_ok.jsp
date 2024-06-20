<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.NamingException" %>

<%@ page import="javax.sql.DataSource" %>

<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="study.BoardDao" %>
<%@ page import="study.BoardTo" %>

<%
	request.setCharacterEncoding( "utf-8" );

	BoardDao boardDao = new BoardDao();
	BoardTo to = new BoardTo();

	to.setPassword( request.getParameter( "password" ));
	to.setContent(request.getParameter( "content" ));
	to.setEmot(request.getParameter( "emot" ).replaceAll( "emot", "" ));
	to.setSeq(request.getParameter( "seq" ));
	to.setSubject(request.getParameter( "subject" ));

	String mail = "";
	if(!request.getParameter( "mail1" ).equals( "" ) && !request.getParameter( "mail2" ).equals( "" )) {
		to.setMail( request.getParameter( "mail1" ) + "@" + request.getParameter( "mail2" ));
	}

	int flag = boardDao.boarddModifyOk(to);
		
	out.println( "<script type='text/javascript'>" );
	if( flag == 0 ) {
		out.println( "alert('글수정에 성공');" );
		out.println( "location.href='board_view1.jsp?seq=" + to.getSeq() + "';" );
	} else if( flag == 1 ) {
		out.println( "alert('비밀번호 오류');" );
		out.println( "history.back();" );
	} else if( flag == 2 ) {
		out.println( "alert('글수정에 실패');" );
		out.println( "history.back();" );
	}
	out.println( "</script>" );
%>