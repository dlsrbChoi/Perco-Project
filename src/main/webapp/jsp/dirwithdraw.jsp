<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ page import="dao.*" %>
<%
  request.setCharacterEncoding("utf-8");

  String uid = request.getParameter("id");

  DirectorDAO dao = new DirectorDAO();
  if (dao.exists(uid) == false) {
  out.print("NA"); //"회원 정보를 찾을 수 없습니다."
  return;
  }
  
  if ((new DirectorDAO()).delete(uid)) {
      out.print("OK"); //"회원 탈퇴가 완료되었습니다."
  }
  else {
      out.print("ER"); //"회원 탈퇴 처리 중 오류가 발생하였습니다."
  }
%>