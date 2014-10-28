package com.cpp2.model;

import com.cpp2.base.BaseModel;

public class User extends BaseModel{

	// model columns
		public final static String COL_ID = "id";
		public final static String COL_SESSIONID = "sessionId";
		public final static String COL_NAME = "name";
		public final static String COL_PASS = "pass";
		public final static String COL_PHONE = "phone";
		public final static String COL_GENDER = "gender";
		public final static String COL_VIP = "vip";
		public final static String COL_SIGN = "sign";
		public final static String COL_EMAIL = "email";
		public final static String COL_FACEURL = "faceurl";
		public final static String COL_BLOGCOUNT = "blogcount";
		public final static String COL_FANSCOUNT = "fanscount";
		public final static String COL_UPTIME = "uptime";
		
		private String id;
		private String sessionId;
		private String name;
		private String pass;
		private String phone;
		private String gender;
		private String vip;
		private String sign;
		private String email;
		private String faceurl;
		private String blogcount;
		private String fanscount;
		private String uptime;
		
		// default is no login
		private boolean isLogin = false;
		
		// single instance for login
		static private User user = null;
		
		static public User getInstance () {
			if (User.user == null) {
				User.user = new User();
			}
			return User.user;
		}
		//make the constructor private for single instance
		private User () {}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getSessionId() {
			return sessionId;
		}

		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPass() {
			return pass;
		}

		public void setPass(String pass) {
			this.pass = pass;
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

		public String getSign() {
			return sign;
		}

		public void setSign(String sign) {
			this.sign = sign;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getFaceurl() {
			return faceurl;
		}

		public void setFaceurl(String faceurl) {
			this.faceurl = faceurl;
		}

		public String getBlogcount() {
			return blogcount;
		}

		public void setBlogcount(String blogcount) {
			this.blogcount = blogcount;
		}

		public String getFanscount() {
			return fanscount;
		}

		public void setFanscount(String fanscount) {
			this.fanscount = fanscount;
		}

		public String getUptime() {
			return uptime;
		}

		public void setUptime(String uptime) {
			this.uptime = uptime;
		}

		public boolean isLogin() {
			return isLogin;
		}

		public void setLogin(boolean isLogin) {
			this.isLogin = isLogin;
		}

		public static User getCustomer() {
			return user;
		}

		public static void setCustomer(User user) {
			User.user = user;
		}
		
		
}
