<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*, com.zchen323.photo.data.*" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
<title>pick one</title>
</head>
<body>
<%
	List<Article> list = (List<Article>)request.getAttribute("articles");
	for(Iterator<Article> it = list.iterator(); it.hasNext();){
		Article a = it.next();
%>
<p>
<a href="photo?id=<%=a.getId() %>"><b><%=a.getTitle() %></b></a> <%=a.getDateString() %><br /><br />
<%=a.getContent() %><br />
</p>
<%} %>
</body>
</html>