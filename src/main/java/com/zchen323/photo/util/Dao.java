package com.zchen323.photo.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zchen323.photo.data.Article;

/**
 * use java low level api
 * 
 * @author zchen323
 *
 */

public class Dao {
	
	static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	public static void updateArticle(Article article){		
		saveArticle(article);
	}
	
	public static void createNewArticle(Article article) throws Exception{
		//try{
		//	getArticalByTitle(article.getTitle());
		//}catch(EntityNotFoundException e){
			saveArticle(article);
		//	return;
		//}
		
		//throw new Exception(" this article already exists");
	}
	
	private static void saveArticle(Article article){
		
		Gson gson = new GsonBuilder().create();
		String gString = gson.toJson(article);
		
		// Entity has kind and identifier
		com.google.appengine.api.datastore.Text text = 
				new com.google.appengine.api.datastore.Text(gString);
		Key key = KeyFactory.createKey("article", article.getId());		
		Entity articleEntity = new Entity(key);
		articleEntity.setProperty("id", article.getId());
		articleEntity.setProperty("title", article.getTitle());
		articleEntity.setProperty("date", article.getDate());
<<<<<<< HEAD
		articleEntity.setProperty("isActive", article.isActive());
=======
>>>>>>> 7ab8c01524bf2e962d8c920dbd1cf8371edf2818
		articleEntity.setProperty("value", text);
		datastore.put(articleEntity);	
		
		try {
			Entity e = datastore.get(key);
			//System.out.println("==== " + ((Text)e.getProperty("value")).getValue());
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}
	
	public static List<Article> getAllArticleWithTitleAndDate(){

		List<Article> list = new ArrayList<Article>();
		
		Query q = new Query("article").addSort("date", SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(q);
		
		for(Entity result : pq.asIterable()){
			Article a = new Article();
			a.setId((String)result.getProperty("id"));
			a.setTitle((String)result.getProperty("title"));
			a.setDate((Date)result.getProperty("date"));
<<<<<<< HEAD
			if(result.getProperty("isActive") != null){
				a.setActive((Boolean)result.getProperty("isActive"));
			}
=======
>>>>>>> 7ab8c01524bf2e962d8c920dbd1cf8371edf2818
			list.add(a);
		}		
		return list;
	}
	
	public static Article getArticalById(String id) throws EntityNotFoundException{
		Key key = KeyFactory.createKey("article", id);
		
		Entity entity = datastore.get(key);
		
		Text text = (Text)entity.getProperty("value");
		
		String gString = text.getValue();
		
		Gson gson = new GsonBuilder().create();
		Article article = gson.fromJson(gString, Article.class);
		
		return article;
	}	
	
	public static List<Article> getArticalByTitle(String title) {
		
		List<Article> list = new ArrayList<Article>();
		
		Filter titleFilter = new FilterPredicate(
			"title",
			FilterOperator.EQUAL,
			title
			);
		Query q = new Query("article").setFilter(titleFilter);
		q.addSort("date", SortDirection.DESCENDING);
		PreparedQuery pq = datastore.prepare(q);
		for(Entity entity : pq.asIterable()){
			Text text = (Text)entity.getProperty("value");		
			String gString = text.getValue();
			Gson gson = new GsonBuilder().create();
			Article article = gson.fromJson(gString, Article.class);
			list.add(article);
		}
		
		return list;
	}
	
	public static void addAdmin(String adminEmail){
		
		if(adminEmail == null || adminEmail.trim().length() == 0)
			return;
		Key key = KeyFactory.createKey("admin", adminEmail);		
		Entity adminEntity = new Entity(key);		
		adminEntity.setProperty("email", adminEmail);
		datastore.put(adminEntity);
		
	}
	public static void removeAdmin(String adminEmail){
		
		if(adminEmail == null || adminEmail.trim().length() == 0)
			return;
		Key key = KeyFactory.createKey("admin", adminEmail);		
		datastore.delete(key);
		
	}	
	
	
	public static List<String> getAdmin(){		
		List<String> list = new ArrayList<String>();
		
		Query q = new Query("admin");
		PreparedQuery pq = datastore.prepare(q);
		
		for(Entity result : pq.asIterable()){
			String adminEmail = (String)result.getProperty("email");
			list.add(adminEmail);
		}
		
		return list;
	}
	
}

