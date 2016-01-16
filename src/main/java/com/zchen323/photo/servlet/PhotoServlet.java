package com.zchen323.photo.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zchen323.photo.data.Article;
import com.zchen323.photo.util.Dao;

public class PhotoServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	 @Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {
		 
		 String id = req.getParameter("id");
		 String title = req.getParameter("title");		 
		 try {
			 
			Article article = null;
			if(id != null){
				// find article by id
				article = Dao.getArticalById(id);
				if(article.isActive()){
					req.setAttribute("article", article);	
					req.getRequestDispatcher("/article.jsp").forward(req, resp);
				}else{
					resp.setStatus(404);
				}

			}else if(title != null){
				// find article by title
				List<Article> list = Dao.getArticalByTitle(title);
				if(list.size() == 1){
					article = list.get(0);
					req.setAttribute("article", article);			
					req.getRequestDispatcher("/article.jsp").forward(req, resp);
					                     
				}else if(list.size() > 1){
					req.setAttribute("articles", list);			
					req.getRequestDispatcher("/article_choice.jsp").forward(req, resp);
				}else{
					throw new Exception();
				}
			}else{
				// list all article
				List<Article> list = Dao.getAllArticleWithTitleAndDate();
				req.setAttribute("articles", list);				
				req.getRequestDispatcher("/article_list.jsp").forward(req, resp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(400);
			resp.setCharacterEncoding("UTF-8");
			if(id != null)
				resp.getWriter().print("Sorry, can't find " + id);
			else
				resp.getWriter().print("Sorry, can't find " + title);
		}
		 
	 }
	 
	 @Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {
		 
		 
	 }
	 
}
