package com.robaone.dbase;

public class HDBSessionData {
	private String username;
	private String host;
	public HDBSessionData(String username,String host){
		this.username = username;
		this.host = host;
	}
	public HDBSessionData(String username) throws Exception {
		java.net.InetAddress i = java.net.InetAddress.getLocalHost();
		this.username = username;
		this.host = i.getHostAddress();
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getHost() {
		return host;
	}
}
