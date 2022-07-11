<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ page import="dao.*" %>
<%
  request.setCharacterEncoding("utf-8");

  String uid = request.getParameter("id");
  String jsonstr = request.getParameter("jsonstr");
  
  DirectorDAO dao = new DirectorDAO();
  if (dao.exists(uid)) { 
      out.print("EX"); //out.print("이미 가입한 회원입니다.");
      return;
  }
  
  if (dao.insert(uid, jsonstr)) {
	  session.setAttribute("id", uid);
	  out.print("OK"); //out.print("회원 가입이 완료되었습니다.");
  }
  else {
      out.print("ER"); //out.print("회원 가입 중 오류가 발생하였습니다.");
  }
%>