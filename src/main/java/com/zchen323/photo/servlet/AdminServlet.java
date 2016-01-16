package com.zchen323.photo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zchen323.photo.data.Article;
import com.zchen323.photo.data.Photo;
import com.zchen323.photo.util.Dao;

public class AdminServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {
		User user = SecurityHelper.getLoginUser(req, resp);
		
		if(user == null){
			return;
		}
		if(!isAuthorized(user)){
			try{
				req.setAttribute("loginUerEmail", user.getEmail());
				req.getRequestDispatcher("/error_not_authorized.jsp").forward(req, resp);
				return;
			}catch(Exception e){
				e.printStackTrace();
				throw new IOException(e);
			}
		}
		
		req.setAttribute("user", user);
		
		String action = req.getParameter("action");		 
		 System.out.println("=== action: " + action);
		 
		 if("add_article".equalsIgnoreCase(action)){
			 try{
				 System.out.println("in: " + action);
				 req.getRequestDispatcher("/admin/new_article.html").forward(req, resp);
				 
			 }catch(Exception e){
				 e.printStackTrace();
			 }
			 
		 }
		 else if("edit".equalsIgnoreCase(action)){
			 String id = req.getParameter("id");
			 try {
				Article article = Dao.getArticalById(id);
				req.setAttribute("article", article);
				req.getRequestDispatcher("/admin/edit_article.jsp").forward(req, resp);
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 }else if("edit_list".equalsIgnoreCase(action)){
			 try {
				List<Article> list = Dao.getAllArticleWithTitleAndDate();
				req.setAttribute("articles", list);		 
				req.getRequestDispatcher("/admin/edit_article_list.jsp").forward(req, resp);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }else if("add_new_user".equalsIgnoreCase(action)){
			 try{
				 List<String> users = Dao.getAdmin();
				// Collections.sort(users);
				 req.setAttribute("users", users);
				 req.getRequestDispatcher("/admin/add_new_user.jsp").forward(req, resp);
			 }catch(Exception e){
				 e.printStackTrace();
			 }
			 
		 }else{
			 
			 try{
				 req.getRequestDispatcher("/admin/admin.jsp").forward(req, resp);
			 }catch(Exception e){
				 e.printStackTrace();
			 }
		 }
		 
	 }

	 @Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {
			User user = SecurityHelper.getLoginUser(req, resp);
			
			if(user == null){
				return;
			}
			if(!isAuthorized(user)){
				try{
					req.getRequestDispatcher("/error_not_authorized.jsp").forward(req, resp);
					return;
				}catch(Exception e){
					e.printStackTrace();
					throw new IOException(e);
				}
			}
			
		 String action = req.getParameter("action");
		 
		 
		 if("add_new".equals(action)){
			 // do add new
			 int count = Integer.parseInt(req.getParameter("count"));
			 Article article = new Article();
			 
			 article.setTitle((String)req.getParameter("title"));
			 article.setContent((String)req.getParameter("content"));
			 article.setAuthor(user.getNickname());
			 article.setActive(true);
			 
			
			 List<Photo> list = new ArrayList<Photo>();
			 for(int i = 1000; i < count; i++){
				 Photo photo = new Photo();
				 photo.setTitle(req.getParameter("imagetitle_" + i));
				 photo.setUrl(req.getParameter("imageurl_" + i));
				 photo.setDescription(req.getParameter("imagedesc_" + i));
				 photo.setActive(true);
				 list.add(photo);
			 }
			 
			 article.setPhotos(list);
			 
			Gson gson = new GsonBuilder().create();
			System.out.println(gson.toJson(article)); 
			 try{
				 Dao.createNewArticle(article);
				 resp.getWriter().print(article.getId());
			 }catch(Exception e){
				 e.printStackTrace();
				 resp.getWriter().print("error: " + e.getMessage());
			 }
			 
		 }else if("update".equals(action)){
			 String id = req.getParameter("id");
			 String title = req.getParameter("title");
			 String content = req.getParameter("content");
			 int count = Integer.parseInt(req.getParameter("count"));
			 String articleChecBoxValue = req.getParameter("articleActive");
			 
			 List<Photo> list = new ArrayList<Photo>();
			 for(int i = 1000; i < count; i++){
				 Photo photo = new Photo();
				 photo.setTitle(req.getParameter("imagetitle_" + i));
				 photo.setUrl(req.getParameter("imageurl_" + i));
				 photo.setDescription(req.getParameter("imagedesc_" + i));
				 String checkBoxValue = req.getParameter("imageactive_" + i);
				 
				 System.out.println("==== checkbox: " + checkBoxValue);
				 
				 if("on".equalsIgnoreCase(checkBoxValue)){
					 photo.setActive(true);
				 }else{
					 photo.setActive(false);
				 }
				 list.add(photo);
			 }
			 
			 Article article = new Article();
			 article.setId(id);
			 article.setTitle(title);
			 article.setContent(content);
			 if("on".equalsIgnoreCase(articleChecBoxValue)){
				 article.setActive(true);
			 }else{
				 article.setActive(false);
			 }
			 
			 article.setPhotos(list);
			 
			 Dao.updateArticle(article);
			 resp.getWriter().print(article.getId());
			 
			 
			 // do update
			 //String count = req.getParameter("count");
			 //System.out.println("===== update count:" + count);
			 
		 }else if("add_new_user".equalsIgnoreCase(action)){
			 String email = req.getParameter("email");
			 Dao.addAdmin(email.toLowerCase());
			 try{
				resp.sendRedirect("/t/_admin");
			 }catch(Exception e){
				 e.printStackTrace();
			 }			 
		 }else if("remove_user".equalsIgnoreCase(action)){
			 String email = req.getParameter("email");
			 Dao.removeAdmin(email);
			 try{
				resp.sendRedirect("/t/_admin");
			 }catch(Exception e){
				 e.printStackTrace();
			 }			 			 
		 }
	 }
	 
	 
	 private boolean isAuthorized(User user){
		 String emai = user.getEmail();
		 if("zchen323@gmail.com".equalsIgnoreCase(emai)
				 || "test@example.com".equalsIgnoreCase(emai)
				 || getAdminList().contains(emai.toLowerCase())){
			 return true;
		 }
		 return false;
	 }
	 
	 private List<String> getAdminList(){
		// List<String> list = Dao.getAdmin();
		// System.out.println("===== admin list:" + list);
		 
		 return Dao.getAdmin();
	 }
}