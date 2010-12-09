package com.robaone.gwt.projectmanager.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserData implements IsSerializable {
	private String username;
	public UserData(){}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}

	
}
