package com.robaone.gwt.projectmanager.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.robaone.gwt.projectmanager.client.ProjectConstants.USER_TYPE;

public class UserData implements IsSerializable {
	private String username;
	private String picture_url;
	private String zip;
	private USER_TYPE accounttype;
	public UserData(){}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public String getPictureUrl() {
		return picture_url;
	}
	public void setPictureUrl(String url){
		this.picture_url = url;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getZip() {
		return zip;
	}
	public USER_TYPE getAccountType() {
		return this.accounttype;
	}
	public void setAccountType(USER_TYPE type){
		this.accounttype = type;
	}
	
}
