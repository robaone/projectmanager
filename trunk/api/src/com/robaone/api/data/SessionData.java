package com.robaone.api.data;

import com.robaone.api.data.jdo.App_credentials_jdo;
import com.robaone.api.data.jdo.User_jdo;

public class SessionData {

	private User_jdo user;
	private String remotehost;
	private App_credentials_jdo credentials;
	private String session_token;

	public void setUser(User_jdo account_record) {
		this.user = account_record;
	}

	public User_jdo getUser() {
		return this.user;
	}

	public String getRemoteHost() {
		return this.remotehost;
	}

	public void setRemoteHost(String host){
		this.remotehost = host;
	}

	public void setCredentials(App_credentials_jdo cred) {
		this.credentials = cred;
		this.setSession_token(cred.getAccess_token());
	}
	
	public App_credentials_jdo getCredentials(){
		return this.credentials;
	}

	public String getSession_token() {
		return session_token;
	}

	public void setSession_token(String session_token) {
		this.session_token = session_token;
	}
}
