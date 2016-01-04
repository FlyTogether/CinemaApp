package com.cpp2.model;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class BmobMessage  extends BmobObject implements Serializable{
	private String from;
	private String data;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

}
