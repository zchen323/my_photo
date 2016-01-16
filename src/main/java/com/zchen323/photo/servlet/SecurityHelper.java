package com.zchen323.photo.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class SecurityHelper {

	public static User getLoginUser(HttpServletRequest request, HttpServletResponse response){		
		UserService userService = UserServiceFactory.getUserService();		
		User user = userService.getCurrentUser();
		if(user == null){
			String url = userService.createLoginURL(request.getRequestURI());
			try {
				response.sendRedirect(url);				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			return user;
		}
		return null;
	}
}
