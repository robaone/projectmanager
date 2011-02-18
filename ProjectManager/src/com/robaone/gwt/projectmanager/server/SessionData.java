package com.robaone.gwt.projectmanager.server;


import com.robaone.gwt.projectmanager.client.UserData;

public class SessionData {
	private UserData user_data;
	private String zipcode = null;
	private String current_host;
	public SessionData(){
	}
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
	public void setCurrentHost(String string) {
		this.current_host = string;
	}
	public String getCurrentHost(){
		return this.current_host;
	}
}
