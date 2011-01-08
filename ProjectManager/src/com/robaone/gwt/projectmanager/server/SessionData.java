package com.robaone.gwt.projectmanager.server;

import com.robaone.gwt.projectmanager.client.UserData;

public class SessionData {
	private UserData user_data;
	private String zipcode = null;
	public UserData getUserData() {
		return this.user_data;
	}
	public void setUserData(UserData data){
		this.user_data = data;
	}
	public String getCurrentZip() {
		if(zipcode == null && user_data != null){
			return user_data.getZip();
		}else if(zipcode != null){
			return zipcode;
		}else{
			return null;
		}
	}
	public void setCurrentZip(String value) {
		zipcode = value;
	}
}
