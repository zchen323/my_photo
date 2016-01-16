<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
<title>Insert title here</title>
</head>
<body>
<h1>Add New User</h1>
<form action="/t/_admin" method="post">
email: <input type="text" name="email" />
<input type="hidden" name="action" value="add_new_user">
<input type="submit" />
</form>

<h1>Remove User</h1>
<form action="/t/_admin" method="post">
email: <input type="text" name="email" />
<input type="hidden" name="action" value="remove_user">
<input type="submit" />
</form>


<ul>
<%
	java.util.List<String> list = (java.util.List<String>)request.getAttribute("users");
	if(list != null)
		for(java.util.Iterator<String> it = list.iterator(); it.hasNext();){
%>
<li>
<%= it.next() %>
</li>
<%} 
%>
</ul>
</body>
</html>