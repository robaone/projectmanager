package com.robaone.gwt.projectmanager.client.data;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.robaone.gwt.projectmanager.client.ProjectConstants.USER_TYPE;

public class UserData implements IsSerializable {
	private String username;
	private String picture_url;
	private String zip;
	private String accounttype;
	private Contractor contractorid;
	private String firstname;
	private String lastname;
	private String phonenumber;
	public static final String USERNAME = "username";
	public static final String PICTUREURL = "pictureurl";
	public static final String ZIP = "zip";
	public static final String ROLE = "role";
	public static final String FIRSTNAME = "firstname";
	public static final String LASTNAME = "lastname";
	public static final String PHONENUMBER = "phonenumber";
	public static final String PASSWORD = "password";
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
	public String getAccountType() {
		return this.accounttype;
	}
	public void setAccountType(String type){
		this.accounttype = type;
	}
	public Contractor getContractor() {
		return this.contractorid;
	}
	public void setContractorid(Contractor con){
		this.contractorid = con;
	}
	public void setFirstname(String value) {
		this.firstname = value;
	}
	public String getFirstname(){
		return this.firstname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	
	
}
