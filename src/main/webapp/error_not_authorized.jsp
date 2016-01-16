<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
<title>Not Authorized</title>
</head>
<body>
Login user email: <%= request.getAttribute("loginUerEmail") %><br />
You are not authorized to view this page.<br />
If you have question, please send email to: <a href="mailto:zchen323@gmail.com">zchen323@gmail.com</a>
</body>
</html>