package com.zchen323.photo.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Article {
	private String id;
	private String title;
	private Date date;
	private String author;
	private String content;
	private List<Photo> photos;
	private boolean active;
	private Date lastUpdate;
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Article(){
		date = new Date();
		id = new SimpleDateFormat("yyyyMMddhhmmss").format(this.date);
	}
	public void setId(String id){
			this.id = id;
	}
	
	public String getId(){
		return this.id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
			this.date = date;
	}
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<Photo> getPhotos() {
		return photos;
	}
	public void setPhotos(List<Photo> list) {
		this.photos = list;
	}
	
	public String getDateString(){
		return new SimpleDateFormat("yyyy-MM-dd").format(this.date);
		
	}

	public String getLastUpdateDateString(){
		return new SimpleDateFormat("yyyy-MM-dd").format(this.lastUpdate);
		
	}	
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	
	
}
