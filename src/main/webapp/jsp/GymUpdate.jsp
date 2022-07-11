<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="dao.DirectorDAO" %>
<%@ page import="util.*" %>
<%
    request.setCharacterEncoding("utf-8");
	
	String uid = null;  String jsonstr = null;
		
	ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
	List items = sfu.parseRequest(request);
	Iterator iter = items.iterator();
	while(iter.hasNext()) {
		FileItem item = (FileItem) iter.next();
		String name = item.getFieldName();
		if(item.isFormField()) {
			String value = item.getString("utf-8");
			if (name.equals("id")) uid = value;
			if (name.equals("jsonstr")) jsonstr = value;
		}		
    }
	
    DirectorDAO dao = new DirectorDAO();

    if (dao.update(uid, jsonstr)) {
      out.print("OK");
    }
    else {
      out.print("ER"); 
    }
%>