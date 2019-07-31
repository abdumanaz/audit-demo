package com.datalkz.demo.user.context;

import com.datalkz.demo.user.roles.UserRoles;

public class UserContext {
	
	private String userName;
	private String userId;
	private UserRoles role;

	public UserContext(String userName, String userId, UserRoles role) {
		super();
		this.userName = userName;
		this.userId = userId;
		this.role = role;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Enum<UserRoles> getRole() {
		return role;
	}

	public void setRole(UserRoles role) {
		this.role = role;
	}
	
	

}

