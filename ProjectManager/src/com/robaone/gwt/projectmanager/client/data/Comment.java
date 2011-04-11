package com.robaone.gwt.projectmanager.client.data;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Comment implements IsSerializable {
	private String goalid;
	public void setGoalId(String id) {
		goalid = id;
	}
	public String getGoalId() {
		return goalid;
	}

}
