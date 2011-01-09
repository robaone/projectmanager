package com.robaone.gwt.projectmanager.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.robaone.gwt.projectmanager.client.ProjectConstants.USER_TYPE;
import com.robaone.gwt.projectmanager.client.data.Contractor;

public class UserData implements IsSerializable {
	private String username;
	private String picture_url;
	private String zip;
	private USER_TYPE accounttype;
	private Contractor contractorid;
	private String firstname;
	private String lastname;
	private String phonenumber;
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
