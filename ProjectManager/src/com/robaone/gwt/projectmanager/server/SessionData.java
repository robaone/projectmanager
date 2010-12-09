package com.robaone.gwt.projectmanager.server;

import com.robaone.gwt.projectmanager.client.UserData;

public class SessionData {
	private UserData user_data;
	public UserData getUserData() {
		return this.user_data;
	}
	public void setUserData(UserData data){
		this.user_data = data;
	}
}
