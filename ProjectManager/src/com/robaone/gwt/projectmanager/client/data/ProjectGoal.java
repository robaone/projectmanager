package com.robaone.gwt.projectmanager.client.data;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ProjectGoal implements IsSerializable {

	public static final String STATUS = "status";
	public static final String DUEDATE = "duedate";
	public static final String NAME = "name";
	public static final String PROJECTID = "projectid";
	private String id;
	private String status;
	private Date duedate;
	private String name;
	private String projectid;

	public void setProjectId(String id) {
		this.projectid = id;
	}

	public void setName(String text) {
		this.name = text;
	}

	public void setDueDate(Date value) {
		this.duedate = value;
	}

	public void setStatus(String text) {
		this.status = text;
	}

	public String getProjectId() {
		return this.projectid;
	}

	public String getName() {
		return this.name;
	}

	public java.util.Date getDueDate() {
		return this.duedate;
	}

	public String getStatus() {
		return this.status;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String string) {
		this.id = string;
	}

}
