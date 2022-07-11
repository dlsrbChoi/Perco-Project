<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ page import="dao.*" %>
<%
    String uid = request.getParameter("id");
	String gymid = request.getParameter("gymid");
	out.print((new GYMDAO()).remove(uid, gymid));
%>