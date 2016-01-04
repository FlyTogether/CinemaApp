package com.cpp2.model;

import com.cpp2.base.BaseModel;

public class MovieComment extends BaseModel{
	
	private String id;
	private String content;
	private String uptime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUptime() {
		return uptime;
	}
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
}
