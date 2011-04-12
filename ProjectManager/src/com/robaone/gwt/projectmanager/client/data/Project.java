package com.robaone.gwt.projectmanager.client.data;

import java.util.Date;


import com.google.gwt.user.client.rpc.IsSerializable;

public class Project implements IsSerializable {
	public static final String STATUS = "status";
	public static String PROJECTNAME = "projectname";
	public static String DESCRIPTION = "description";
	public static String DUEDATE = "due_date";
	public static String ESTIMATEDHOURS = "est_hours";
	public static String IMPORTANT = "important";
	public static String TAGS = "tags";
	public static String ASSIGNMENTS = "assignments";
	private String projectname;
	private String description;
	private Date due_date;
	private double est_hours;
	private String[] tags;
	private String[] assignments;
	private boolean important;
	private String id;
	private String status;
	public void setProjectName(String projectname) {
		this.projectname = projectname;
	}
	public String getProjectName() {
		return projectname;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}
	public Date getDue_date() {
		return due_date;
	}
	public void setEst_hours(double est_hours) {
		this.est_hours = est_hours;
	}
	public double getEst_hours() {
		return est_hours;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public String[] getTags() {
		return tags;
	}
	public void setAssignments(String[] assignments) {
		this.assignments = assignments;
	}
	public String[] getAssignments() {
		return assignments;
	}
	public void setImportant(boolean important) {
		this.important = important;
	}
	public boolean isImportant() {
		return important;
	}
	public void setId(String string) {
		this.id = string;
	}
	public String getId(){
		return this.id;
	}
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String sts){
		this.status = sts;
	}
	
}
