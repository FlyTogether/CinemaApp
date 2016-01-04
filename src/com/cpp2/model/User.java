package com.cpp2.model;

import java.util.Date;

import com.cpp2.base.BaseModel;

public class User extends BaseModel {
	
//	public final static String COL_ID = "id";
//	public final static String COL_SID = "sid";
//	public final static String COL_NAME = "name";
//	public final static String COL_PASS = "pass";
//	public final static String COL_SIGN = "sign";
//	public final static String COL_FACE = "face";
//	public final static String COL_FACEURL = "faceurl";
//	public final static String COL_BLOGCOUNT = "blogcount";
//	public final static String COL_FANSCOUNT = "fanscount";
//	public final static String COL_UPTIME = "uptime";
	
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getVip() {
		return vip;
	}

	public void setVip(String vip) {
		this.vip = vip;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getConsumption() {
		return consumption;
	}

	public void setConsumption(double consumption) {
		this.consumption = consumption;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}

	private String username;
	private String password;

	private String face;

	private String phone;
	private String gender;
	private String vip;

	private String state;
	private Date birthday;
	private String email;
	private double consumption;			
	// default is no login
	private boolean isLogin = false;
	
	// single instance for login
	static private User customer = null;
	
	static public User getInstance () {
		if (User.customer == null) {
			User.customer = new User();
		}
		return User.customer;
	}
	
	public User () {}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}
	
	
	
	
}