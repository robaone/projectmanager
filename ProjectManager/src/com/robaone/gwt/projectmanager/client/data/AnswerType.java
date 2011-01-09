package com.robaone.gwt.projectmanager.client.data;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AnswerType implements IsSerializable {
	private String description;
	private String value;
	public AnswerType(){}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
