package com.robaone.gwt.projectmanager.client.data;

import com.google.gwt.user.client.rpc.IsSerializable;

public class FeedItem implements IsSerializable {
	private String title;
	private String summary;
	private String referenceid;
	private String iconurl;
	private String datetime;
	public FeedItem(){}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getSummary() {
		return summary;
	}
	public void setReferenceid(String referenceid) {
		this.referenceid = referenceid;
	}
	public String getReferenceid() {
		return referenceid;
	}
	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}
	public String getIconurl() {
		return iconurl;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public String getDatetime() {
		return datetime;
	}

	
}
