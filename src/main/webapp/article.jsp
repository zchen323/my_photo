<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.zchen323.photo.data.*, java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width" />
<%
	Article a = (Article)request.getAttribute("article");

%>
<title><%= a.getTitle() %></title>
	<style type="text/css">
	img {
    max-width: 100%;
 	height:auto;
 	margin: 0;
 	padding: 0;
 	border: 0;
	}

	</style>

</head>
<body>

<h1><%= a.getTitle() %></h1>
<%= a.getDateString() %>&nbsp;&nbsp;<font color='white'><%= a.getAuthor() %></font>
<p>
<%= a.getContent().replaceAll("\n", "<br />") %>
</p>
<%
	List<Photo> list = a.getPhotos();
	for(Iterator<Photo>it = list.iterator(); it.hasNext();){
		Photo p = it.next();
		if(!p.isActive()){
			continue;
		}
%>
<p>
<%= p.getTitle() %><br />
<img src="<%= p.getUrl() %>" /><br />
<%= p.getDescription() %>
</p>
<%		
	}
%>

<br />
<br />

</body>
</html>