package com.robaone.gwt.projectmanager.client.data;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Contractor implements IsSerializable {
	private Integer id;
	private String name;
	private String summary;
	private String info;
	private String logo_url;
	private Question[] questions;
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getSummary() {
		return summary;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getInfo() {
		return info;
	}
	public void setLogo_url(String logo_url) {
		this.logo_url = logo_url;
	}
	public String getLogo_url() {
		return logo_url;
	}
	public void setQuestions(Question[] questions) {
		this.questions = questions;
	}
	public Question[] getQuestions() {
		return questions;
	}
}
