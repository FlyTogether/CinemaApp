package com.cpp2.base;

import com.cpp2.model.User;

public class BaseAuth {
	
	static public boolean isLogin () {
		User user = User.getInstance();
		if (user.isLogin() == true) {
			return true;
		}
		return false;
	}
	
	static public void setLogin (Boolean status) {
		User user = User.getInstance();
		user.setLogin(status);
	}
	
	static public void setUser (User u) {
		User user = User.getInstance();
		user.setId(u.getId());
//		user.setSid(u.getSid());
		user.setName(u.getName());
		user.setSign(u.getSign());
		user.setFaceurl(u.getFaceurl());
	}
	
	static public User getUser () {
		return User.getInstance();
	}
}