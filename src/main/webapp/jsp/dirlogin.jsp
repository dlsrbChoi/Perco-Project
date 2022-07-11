<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ page import="dao.DirectorDAO" %>
<%
	request.setCharacterEncoding("utf-8");
	String uid = request.getParameter("id");
	String upass = request.getParameter("ps");
	
	DirectorDAO dao = new DirectorDAO();
	int code = dao.login(uid, upass);
	if (code == 1) {
		out.print("NE"); //"아이디가 존재하지 않습니다."
	}
	else if (code == 2) {
		out.print("PE"); //"패스워드가 일치하지 않습니다."
	}
	else {
		session.setAttribute("id", uid);
		out.print("OK"); //response.sendRedirect("main.html");
	}
%>