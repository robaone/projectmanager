package com.robaone.gwt.projectmanager.client.data;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Comment implements IsSerializable {
	private String goalid;
	private String id;
	private UserData userdata;
	private Double hours;
	private Date workdate;
	private Date modifieddate;
	private String comment;
	public void setGoalId(String id) {
		goalid = id;
	}
	public String getGoalId() {
		return goalid;
	}
	public void setId(String string) {
		this.id = string;
	}
	public String getId(){
		return this.id;
	}
	public void setHours(Double hours) {
		this.hours = hours;
	}
	public Double getHours(){
		return hours;
	}
	public void setWorkDate(Date date) {
		this.workdate = date;
	}
	public Date getWorkDate(){
		return workdate;
	}
	public void setModifiedDate(Date date) {
		this.modifieddate = date;
	}
	public Date getModifiedDate(){
		return modifieddate;
	}
	public void setComment(String string) {
		this.comment = string;
	}
	public String getComment(){
		return this.comment;
	}
	public void setUserData(UserData userdata) {
		this.userdata = userdata;
	}
	public UserData getUserData() {
		return userdata;
	}
}
