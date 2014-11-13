package com.cpp2.model;

import java.util.Date;

import com.cpp2.base.BaseModel;

public class Movie extends BaseModel{
	
//	public final static String COL_ID ="id" ;
//	public final static String COL_MOVIENAME = "movieName" ;
//	public final static String COL_MOVIEIMAGE = "movieImage";
//	public final static String COL_DIRECTOR = "director" ;
//	public final static String COL_SHOWTIME = "showTime" ;
//	public final static String COL_RUNTIME = "runTime" ;
//	public final static String COL_CASTACTOR = "castActor" ;
//	public final static String COL_LANGUAGE = "language" ;
//	public final static String COL_STYLE = "style" ;
//	public final static String COL_AREA = "area" ;
//	public final static String COL_TYPE = "type" ;
//	public final static String COL_INTRODUCTION = "introduction" ;
//	public final static String COL_PRICE = "price" ;
//	public final static String COL_IMAGE = "image" ;
//	public final static String COL_POPULARITY = "popularity" ;
	
	private int id;
	private String state;
	
	private String name;
	private String image;
	private String director;
	private String showtime;
	private String runtime;
	private String castActor;
	private String language;
	private String style;//3D or 2D
	private String area;
	private String type;
	private String introduction;
	private String price;
	private String popularity;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getShowTime() {
		return showtime;
	}
	public void setShowTime(String showTime) {
		this.showtime = showTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCastActor() {
		return castActor;
	}
	public void setCastActor(String castActor) {
		this.castActor = castActor;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPopularity() {
		return popularity;
	}
	public void setPopularity(String popularity) {
		this.popularity = popularity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getRuntime() {
		return runtime;
	}
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}
	
}
