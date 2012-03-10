package com.robaone.gwt.framework.client.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ContactDetails implements Serializable {
	private int id;
	private String displayName;
	public ContactDetails() {}
	public ContactDetails(int id,String displayName){
		this.id = id;
		this.displayName = displayName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
