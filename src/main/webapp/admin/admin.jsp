<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.google.appengine.api.users.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
<title>Admin Main</title>
</head>
<%
	User user = (User)request.getAttribute("user");
%>
<b>Login User Info:</b>
<ul>
	<li>AuthDomain: <%= user.getAuthDomain() %></li>
	<li>Email: <%= user.getEmail() %></li>
	<li>FederatedIdentity: <%= user.getFederatedIdentity() %></li>
	<li>Nickname: <%= user.getNickname() %></li>
	<li>UserId: <%= user.getUserId() %></li>
</ul>

<h1>Manage your articles</h1>
<body>
<a href="_admin?action=add_article">Add New Article</a><br />
<a href="_admin?action=edit_list">List all articles</a><br />
<a href="_admin?action=add_new_user">Add new user</a>
</body>
</html>